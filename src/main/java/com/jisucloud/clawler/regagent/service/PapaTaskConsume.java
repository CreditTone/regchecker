package com.jisucloud.clawler.regagent.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.deep007.spiderbase.util.TimerRecoder;
import com.jisucloud.clawler.regagent.interfaces.Account;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PapaTaskConsume{
	
	@Autowired
	private MongoDatabase mongoDatabase;
	
	private MongoCollection<Document> collection;
	
	private int threadNums = 50;
	
	private List<CustomerThread> customerThreads = new ArrayList<>();
	
	@Autowired
	private PapaTaskProduce papaTaskService;
	
	/**
	 * 所有任务执行的总时间
	 */
	private BigDecimal taskExecuteTimeAsSecond = new BigDecimal(0);
	
	/**
	 * 执行成功任务的总个数
	 */
	private AtomicInteger gdpAtomic = new AtomicInteger();
	
	@Data
	public class Status {
		
		private int taskNums;
		
		private int aliveThreadNums;
		
		private int gdp;
		
		private float averageExeTime;
	}
	
	public Status getStatus() {
		Status status = new Status();
		status.taskNums = papaTaskService.getPapaTaskSize();
		status.aliveThreadNums = threadNums;
		status.gdp = gdpAtomic.get();
		status.averageExeTime = taskExecuteTimeAsSecond.divide(new BigDecimal(status.gdp)).floatValue();
		return status;
	}
	
	@PostConstruct
	private void init() {
		collection = mongoDatabase.getCollection("account");
		for (int i = 0; i < threadNums; i++) {
			CustomerThread customerThread = new CustomerThread();
			customerThread.start();
			customerThreads.add(customerThread);
		}
	}
	
	public final class CustomerThread extends Thread {
		
		public final String id = UUID.randomUUID().toString().replaceAll("\\-", "");
		
		public PapaTask currentPapaTask = null;

		@Override
		public void run() {
			while (true) {
				currentPapaTask = papaTaskService.takePapaTask();
				if (currentPapaTask != null) {
					PapaSpiderClassRunnable papaSpiderClassRunnable = new PapaSpiderClassRunnable(currentPapaTask);
					papaSpiderClassRunnable.execute();
				}
				
			}
		}
	}
	
	/**
	 * 具体撞库任务类
	 * @author stephen
	 *
	 */
	@Data
	public final class PapaSpiderClassRunnable {
		
		private PapaTask papaTask = null;
		private boolean isStarted = false;
		private boolean isDone = false;
		
		public PapaSpiderClassRunnable(PapaTask papaTask) {
			this.papaTask = papaTask;
		}

		public void execute() {
			isStarted = true;
			try {
				TimerRecoder timerRecoder = new TimerRecoder();
				@SuppressWarnings("unchecked")
				Class<? extends PapaSpider> clz = (Class<? extends PapaSpider>) Class.forName(papaTask.getPapaClz());
				log.info(papaTask.getTelephone()+"_执行PapaSpiderClassRunnable:" + papaTask.getPapaClz());
				PapaSpiderConfig papaSpiderConfig = clz.getAnnotation(PapaSpiderConfig.class);
				PapaSpider instance = clz.newInstance();
				boolean registed = false;
				String username = null;
				timerRecoder.start();
				if (papaTask.getTelephone() != null) {
					registed = instance.checkTelephone(papaTask.getTelephone());
					username = papaTask.getTelephone();
				}else if (papaTask.getEmail() != null){
					registed = instance.checkEmail(papaTask.getEmail());
					username = papaTask.getEmail();
				}
				if (username != null) {
					taskExecuteTimeAsSecond.add(new BigDecimal(timerRecoder.consumeTimeAsSecond()));
					gdpAtomic.incrementAndGet();
					Account data = Account.builder()
							.username(username)
							.registed(registed)
							.home(papaSpiderConfig.home())
							.platform(papaSpiderConfig.platform())
							.platformName(papaSpiderConfig.platformName())
							.platformMsg(papaSpiderConfig.message())
							.addTag(papaSpiderConfig.tags())
							.useTime(timerRecoder.getText())
							.fields(instance.getFields()).build();
					saveAccount(data);
				}
			} catch (Exception e) {
				log.warn("任务("+papaTask.getTelephone()+")撞库"+papaTask.getPapaClz()+"发生异常", e);
			}
			isDone = true;
		}
		
		public boolean isDone() {
			return isDone;
		}

	}
	
	
	private void saveAccount(Account data) {
		Bson condition = Filters.and(Filters.eq("username", data.getUsername()), Filters.eq("platform", data.getPlatform()));
		try {
			Document doc = Document.parse(JSON.toJSONString(data));
			Document cache = collection.find(condition).first();
			if (cache == null) {
				cache = doc;
			}
			cache.put("_updateTime", System.currentTimeMillis());
			collection.updateOne(condition, new Document("$set", cache), new UpdateOptions().upsert(true));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
