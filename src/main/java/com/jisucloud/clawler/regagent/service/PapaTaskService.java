package com.jisucloud.clawler.regagent.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.deep007.spiderbase.util.MemoryCacheAsRedis;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PapaTaskService {
	
	private final MemoryCacheAsRedis usernameFiler = new MemoryCacheAsRedis();
	
	public static final String PAPATASK_QUEUE_KEY = "papataskclz_queue_key";

	@Autowired
	private MongoDatabase mongoDatabase;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private final Map<String, TaskStatus> taskCostInfos = new ConcurrentHashMap<String, TaskStatus>(){
		public TaskStatus get(Object key) {
			TaskStatus result = super.get(key);
			if (result == null) {
				result = new TaskStatus((String) key);
				put((String) key, result);
			}
			return result;
		};
	};
	
	private AtomicInteger queueTaskAtomic = new AtomicInteger();
	
	@PostConstruct
	private void init() {
		Long papaTaskNums = redisTemplate.opsForList().size(PAPATASK_QUEUE_KEY);
		if (papaTaskNums != null) {
			queueTaskAtomic.addAndGet(papaTaskNums.intValue());
		}
		
	}
	
	/**
	 * 分配任务，返回任务id
	 * @param username 必须，手机号或邮箱
	 * @param name 可选
	 * @param idcard 可选
	 * @return
	 */
	public synchronized String allocTask(String username, String name, String idcard) {
		String id = UUID.randomUUID().toString();
		TaskStatus statusInfo = taskCostInfos.get(id);
		if (username != null && !usernameFiler.contains(username)) {
			usernameFiler.set(username, true, 3600 * 24);
			log.warn("添加任务:"+username+",id:"+id);
			Set<String> excludePlatforms = findRegistePlatforms(username);
			for (Class<? extends PapaSpider> clz : TestValidPapaSpiderService.TEST_SUCCESS_PAPASPIDERS) {
				PapaSpiderConfig papaSpiderConfig = clz.getAnnotation(PapaSpiderConfig.class);
				try {
					if (excludePlatforms.contains(papaSpiderConfig.platform())) {
						log.info("任务(" + username + "),不需要撞库平台."+papaSpiderConfig.platform());
						continue;
					}
					PapaTask papaTask = PapaTask.builder().telephone(username).id(id).papaClz(clz.getName()).name(name).idcard(idcard).build();
					statusInfo.addPapaClzName(clz.getName());
					redisTemplate.opsForList().rightPush(PAPATASK_QUEUE_KEY, JSON.toJSONString(papaTask));
					queueTaskAtomic.incrementAndGet();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}
	
	public PapaTask takePapaTask() {
//		long papaTaskNums = redisTemplate.opsForList().size(PAPATASK_QUEUE_KEY);
//		if (papaTaskNums > 5000) {
//			String rename = PAPATASK_QUEUE_KEY+"_bak_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//			log.warn("任务堆积严重，移除任务列表 " + PAPATASK_QUEUE_KEY + " " + papaTaskNums + " " + rename);
//			redisTemplate.rename(PAPATASK_QUEUE_KEY, rename);
//		}
		while(true) {
			String str = redisTemplate.opsForList().leftPop(PAPATASK_QUEUE_KEY, 10, TimeUnit.SECONDS);
			if (str != null) {
				PapaTask papaTask = JSON.parseObject(str, PapaTask.class);
				TaskStatus statusInfo = taskCostInfos.get(papaTask.getId());
				statusInfo.costPapaClzName(papaTask.getPapaClz());
				queueTaskAtomic.decrementAndGet();
				return papaTask;
			}
		}
	}
	
	public Set<String> findRegistePlatforms(String username) {
		Set<String> platforms = new HashSet<>();
		MongoCollection<Document> collection = mongoDatabase.getCollection("account");
		Bson condition = Filters.and(Filters.eq("username", username),Filters.eq("registed", true));
		MongoCursor<Document> cursor = collection.find(condition).projection(Projections.include("platform")).iterator();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			platforms.add(doc.getString("platform"));
		}
		return platforms;
	}
	
	public int getPapaTaskSize() {
		return queueTaskAtomic.get();
	}
	
	public TaskStatus getTaskStatus(String id) {
		if (id != null && taskCostInfos.containsKey(id)) {
			return taskCostInfos.get(id);
		}
		return null;
	}
	
}
