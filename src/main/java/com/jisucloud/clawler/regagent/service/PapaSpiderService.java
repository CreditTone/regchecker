package com.jisucloud.clawler.regagent.service;

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
import com.jisucloud.clawler.regagent.util.CountableThreadPool;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PapaSpiderService extends Thread {
	
	@Autowired
	private MongoDatabase mongoDatabase;
	
	private MongoCollection<Document> collection;
	
	private CountableThreadPool trehadPool = new CountableThreadPool(100);
	
	@Autowired
	private PapaTaskService papaTaskService;
	
	
	@Data
	public class Status {
		
		private long taskNums; 
		
		private int aliveThreadNums; 
	}
	
	public Status getStatus() {
		Status status = new Status();
		status.taskNums = papaTaskService.getPapaTaskSize();
		status.aliveThreadNums = trehadPool.getThreadAlive();
		return status;
	}
	
	@PostConstruct
	private void init() {
		start();
		collection = mongoDatabase.getCollection("account");
	}
	
	@Override
	public void run() {
		PapaTask papaTask = null;
		while (true) {
			try {
				trehadPool.waitIdleThread();
			} catch (Exception e) {
				log.warn("takePapaTask等待线程超时,服务终止", e);
				break;
			}
			papaTask = papaTaskService.takePapaTask();
			trehadPool.execute(new PapaSpiderClassRunnable(papaTask));
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
		private boolean isStarted = false;
		private boolean isDone = false;
		
		public PapaSpiderClassRunnable(PapaTask papaTask) {
			this.papaTask = papaTask;
		}

		@Override
		public void run() {
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
