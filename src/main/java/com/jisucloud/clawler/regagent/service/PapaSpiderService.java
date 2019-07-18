package com.jisucloud.clawler.regagent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jisucloud.clawler.regagent.http.OKHttpUtil;
import com.jisucloud.clawler.regagent.util.CountableFiberPool;
import com.jisucloud.clawler.regagent.util.CountableThreadPool;
import com.jisucloud.clawler.regagent.util.ReflectUtil;
import com.jisucloud.clawler.regagent.util.TimerRecoder;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Service
public class PapaSpiderService extends Thread {
	
	public static final String PAPATASK_QUEUE_KEY = "papatask_queue_key";
	
	private Set<String> taskIdFiler = new HashSet<String>();

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();
	
	private CountableThreadPool trehadPool = new CountableThreadPool(1000);
	
	@PostConstruct
	private void init() {
		start();
	}
	
	public synchronized void addPapaTask(PapaTask papaTask) {
		if (papaTask != null && !taskIdFiler.contains(papaTask.getId())) {
			log.warn("添加任务:"+papaTask);
			redisTemplate.opsForList().rightPush(PAPATASK_QUEUE_KEY, JSON.toJSONString(papaTask));
			taskIdFiler.add(papaTask.getId());
		}
	}
	
	private PapaTask takePapaTask() {
		while(true) {
			String str = redisTemplate.opsForList().leftPop(PAPATASK_QUEUE_KEY, 10, TimeUnit.SECONDS);
			if (str != null) {
				return JSON.parseObject(str, PapaTask.class);
			}
		}
	}
	
	@Override
	public void run() {
		PapaTask papaTask = null;
		while (true) {
			papaTask = takePapaTask();
			trehadPool.waitIdleThread();
			log.info("执行任务:"+papaTask);
			trehadPool.execute(new PapaSpiderTaskRunnable(papaTask));
		}
	}
	
	public final class PapaSpiderTaskRunnable implements Runnable {
		
		private PapaTask papaTask = null;
		//private List<Fiber<Void>> fibers = new ArrayList<>();
		private List<PapaSpiderClassRunnable> runnables = new ArrayList<>();
		
		public PapaSpiderTaskRunnable(PapaTask papaTask) {
			this.papaTask = papaTask;
		}

		@Override
		public void run() {
			log.info("开始任务("+papaTask.getId()+")");
			TimerRecoder timerRecoder = new TimerRecoder().start();
			if (System.getProperty("os.name").toLowerCase().contains("mac")) {//开发模式只测试10个
				log.info("开发模式只测试10个");
				int nums = 0;
				for (Class<? extends PapaSpider> clz : TestValidPapaSpiderService.TEST_SUCCESS_PAPASPIDERS) {
					PapaSpiderClassRunnable papaSpiderClassRunnable = new PapaSpiderClassRunnable(papaTask, clz);
					trehadPool.waitIdleThread();
					trehadPool.execute(papaSpiderClassRunnable);
					runnables.add(papaSpiderClassRunnable);
					nums++;
					if (nums >= 10) {
						break;
					}
				}
			}else {
				log.info("生产模式");
				for (Class<? extends PapaSpider> clz : TestValidPapaSpiderService.TEST_SUCCESS_PAPASPIDERS) {
					PapaSpiderClassRunnable papaSpiderClassRunnable = new PapaSpiderClassRunnable(papaTask, clz);
					trehadPool.execute(papaSpiderClassRunnable);
					runnables.add(papaSpiderClassRunnable);
				}
			}
			log.info("任务("+papaTask.getId()+")，启动："+runnables.size()+"个协程.");
			waitCheckFinished();
			notifyFinished(papaTask);
			String useTime = timerRecoder.getText();
			log.info("任务("+papaTask.getId()+")结束。用时"+useTime+",成功撞库平台xx个,失败xx个。");
		}
		
		private void waitCheckFinished() {
			if (runnables.isEmpty()) {
				return;
			}
			while (true) {
				boolean isDone = false;
				for (PapaSpiderClassRunnable runnable : runnables) {
					isDone = runnable.isDone();
					if (!isDone) {
						log.info("任务"+runnable.getPapaSpiderClz().getSimpleName()+"还未完成");
						break;
					}
				}
				if (isDone) {
					break;
				}
				try {
					Strand.sleep(10000);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
	}
	
	/**
	 * 具体撞库任务
	 * @author stephen
	 *
	 */
	@Data
	public final class PapaSpiderClassRunnable implements Runnable {
		
		private PapaTask papaTask = null;
		private Class<? extends PapaSpider> papaSpiderClz;
		private boolean isDone = false;
		
		public PapaSpiderClassRunnable(PapaTask papaTask, Class<? extends PapaSpider> papaSpiderClz) {
			this.papaTask = papaTask;
			this.papaSpiderClz = papaSpiderClz;
		}

		@Override
		public void run() {
			if (papaTask.getTelephone() != null) {
				try {
					PapaSpider instance = null;
					instance = papaSpiderClz.newInstance();
					if (papaTask.isNeedlessCheck(instance.platform())) {
						log.info("任务(" + papaTask.getId() + "),不需要撞库平台"+instance.platform());
						return;
					}
					notifyTelephone(papaTask, instance, instance.checkTelephone(papaTask.getTelephone()));
				} catch (Exception e) {
					log.warn("任务("+papaTask.getId()+")撞库"+papaSpiderClz.getName()+"失败", e);
				}
			}
			isDone = true;
		}
		
		public boolean isDone() {
			return isDone;
		}

	}
	
	
	private void notifyFinished(PapaTask papaTask) {
		Map<String,Object> result = new HashMap<>();
		result.put("method", "finished");
		postResult(papaTask, result);
	}
	
	private void notifyTelephone(PapaTask papaTask, PapaSpider instance, boolean registed) {
		Map<String,Object> result = new HashMap<>();
		result.put("method", "notify");
		Map<String, String> fields = instance.getFields();
		if (fields == null) {
			fields = new HashMap<>();
		}
		Account data = Account.builder()
			.username(papaTask.getTelephone())
			.registed(registed)
			.platform(instance.platform())
			.platformName(instance.platformName())
			.platformMsg(instance.message())
			.addTag(instance.tags())
			.fields(fields).build();
		result.put("data", data);
		postResult(papaTask, result);
	}

	private void postResult(PapaTask papaTask, Map<String, Object> result) {
		result.put("id", papaTask.getId());
		String url = papaTask.getCallurl();
		RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"), JSON.toJSONString(result));
		Request request = new Request.Builder().url(url)
				.post(requestBody)
				.build();
		Response response = null;
		for (int i = 0; i < 3; i++) {
			try {
				response = okHttpClient.newCall(request).execute();
				if (response.code() != 200) {
					log.warn("推送结果失败:"+papaTask.getId() + "status:" + response.code());
					continue;
				}
			} catch (Exception e) {
				log.warn("推送结果异常:"+papaTask.getId(), e);
				continue;
			}finally {
				if (response != null) {
					response.body().close();
				}
			}
			if (result.containsKey("data") && ReflectUtil.isInstance(Account.class, result.get("data"))) {
				Account data = (Account) result.get("data");
				log.info("推送结果成功:"+papaTask.getId() + ",platform:" + data.getPlatform());
			}else {
				log.info("推送结果成功:"+papaTask.getId() + ",data:" + result);
			}
			break;
		}
	}
	
}
