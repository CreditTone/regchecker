package com.jisucloud.clawler.regagent.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jisucloud.clawler.regagent.http.OKHttpUtil;
import com.jisucloud.clawler.regagent.util.CountableThreadPool;
import com.jisucloud.clawler.regagent.util.TimerRecoder;

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

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();
	
	private CountableThreadPool countableThreadPool = new CountableThreadPool(50);
	
	@PostConstruct
	private void init() {
		start();
	}
	
	public void addPapaTask(PapaTask papaTask) {
		if (papaTask != null) {
			log.warn("添加任务:"+papaTask);
			redisTemplate.opsForList().rightPush(PAPATASK_QUEUE_KEY, JSON.toJSONString(papaTask));
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
	
	private void waitIdleThread() {
		while(countableThreadPool.getIdleThreadCount() <= 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		PapaTask papaTask = null;
		while (true) {
			papaTask = takePapaTask();
			waitIdleThread();
			countableThreadPool.execute(new PapaSpiderRunnable(papaTask));
		}
	}
	
	public final class PapaSpiderRunnable implements Runnable {
		
		private PapaTask papaTask = null;
		
		public PapaSpiderRunnable(PapaTask papaTask) {
			this.papaTask = papaTask;
		}

		@Override
		public void run() {
			log.info("开始任务("+papaTask.getId()+")");
			TimerRecoder timerRecoder = new TimerRecoder().start();
			int successCount = 0;
			int failureCount = 0;
			for (Class<? extends PapaSpider> clz : TestValidPapaSpiderService.TEST_SUCCESS_PAPASPIDERS) {
				if (papaTask.getTelephone() != null) {
					try {
						PapaSpider instance = null;
						instance = clz.newInstance();
						if (papaTask.isNeedlessCheck(instance.platform())) {
							log.info("任务(" + papaTask.getId() + "),不需要撞库平台"+instance.platform());
							continue;
						}
						notifyTelephone(instance, instance.checkTelephone(papaTask.getTelephone()));
						successCount ++;
					} catch (Exception e) {
						failureCount ++;
						log.warn("任务("+papaTask.getId()+")撞库"+clz.getName()+"失败", e);
					}
				}
			}
			String useTime = timerRecoder.getText();
			log.info("任务("+papaTask.getId()+")结束。用时"+useTime+",成功撞库平台"+successCount+"个,失败"+failureCount+"个。");
		}
		
		private void notifyTelephone(PapaSpider instance, boolean registed) {
			String url = papaTask.getCallurl();
			Map<String,Object> result = new HashMap<>();
			result.put("id", papaTask.getId());
			result.put("tel", papaTask.getTelephone());
			result.put("registed", registed);
			result.put("platform", instance.platform());
			result.put("platformName", instance.platformName());
			result.put("message", instance.message());
			Map<String, String> fields = instance.getFields();
			if (fields == null) {
				fields = new HashMap<>();
			}
			result.put("fields", fields);
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
				}
				break;
			}
		}
		
	}
	
}
