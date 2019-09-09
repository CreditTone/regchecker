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
import com.jisucloud.clawler.regagent.service.impl.b2c.YouZanSpider;
import com.jisucloud.clawler.regagent.service.impl.knowledge.ZhiHuSpider;
import com.jisucloud.clawler.regagent.service.impl.life.BaiduSpider;
import com.jisucloud.clawler.regagent.service.impl.life.DaZhongDianPingSpider;
import com.jisucloud.clawler.regagent.service.impl.life.MeiTuanSpider;
import com.jisucloud.clawler.regagent.service.impl.life.QiHu360Spider;
import com.jisucloud.clawler.regagent.service.impl.life._58Spider;
import com.jisucloud.clawler.regagent.service.impl.news.MeiTuWangSpider;
import com.jisucloud.clawler.regagent.service.impl.news.QuTouTiaoSpider;
import com.jisucloud.clawler.regagent.service.impl.news.TouTiaoSpider;
import com.jisucloud.clawler.regagent.service.impl.news.ZhongGuanCunZaiXianSpider;
import com.jisucloud.clawler.regagent.service.impl.pay.LaKaLaSpider;
import com.jisucloud.clawler.regagent.service.impl.pay.YunFuTongSpider;
import com.jisucloud.clawler.regagent.service.impl.saas.YunZhiJiaSpider;
import com.jisucloud.clawler.regagent.service.impl.social.MainMainSpider;
import com.jisucloud.clawler.regagent.service.impl.social.QQSpider;
import com.jisucloud.clawler.regagent.service.impl.social.ShiJiJiaYuanSpider;
import com.jisucloud.clawler.regagent.service.impl.social.WeiXinSpider;
import com.jisucloud.clawler.regagent.service.impl.trip.DiDiSpider;
import com.jisucloud.clawler.regagent.service.impl.trip.QunarSpider;
import com.jisucloud.clawler.regagent.service.impl.trip.Web12306Spider;
import com.jisucloud.clawler.regagent.service.impl.util.MingPianQuanNengWangSpider;
import com.jisucloud.clawler.regagent.service.impl.util.QiChaChaSpider;
import com.jisucloud.clawler.regagent.service.impl.util.XunLeiSpider;
import com.jisucloud.clawler.regagent.service.impl.video.LeTVSpider;
import com.jisucloud.clawler.regagent.service.impl.video.PPTVSpider;
import com.jisucloud.clawler.regagent.service.impl.work.HighpinSpider;
import com.jisucloud.clawler.regagent.service.impl.work.LiePinWangSpider;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PapaTaskProduce {
	
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
	
	public void pushTaskToQueue(PapaTask papaTask) {
		try {
			redisTemplate.opsForList().rightPush(PAPATASK_QUEUE_KEY, JSON.toJSONString(papaTask));
			TaskStatus statusInfo = taskCostInfos.get(papaTask.getId());
			statusInfo.addPapaClzName(papaTask.getPapaClz());
			queueTaskAtomic.incrementAndGet();
		} catch (Exception e) {
			e.printStackTrace();
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
			//扫描全局所有可执行的任务类
//			for (Class<? extends PapaSpider> clz : TestValidPapaSpiderService.TEST_SUCCESS_PAPASPIDERS) {
//				PapaSpiderConfig papaSpiderConfig = clz.getAnnotation(PapaSpiderConfig.class);
//				if (excludePlatforms.contains(papaSpiderConfig.platform())) {
//					log.info("任务(" + username + "),不需要撞库平台."+papaSpiderConfig.platform());
//					continue;
//				}
//				PapaTask papaTask = PapaTask.builder().telephone(username).id(id).papaClz(clz.getName()).name(name).idcard(idcard).build();
//				pushTaskToQueue(papaTask);
//			}
			//xingkun的任务
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(DaZhongDianPingSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(MeiTuanSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(LaKaLaSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(YunFuTongSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(YunZhiJiaSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(BaiduSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(QiHu360Spider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(_58Spider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(QuTouTiaoSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(MeiTuWangSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(ZhongGuanCunZaiXianSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(TouTiaoSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(HighpinSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(LiePinWangSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(LeTVSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(PPTVSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(XunLeiSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(QiChaChaSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(MingPianQuanNengWangSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(DiDiSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(QunarSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(Web12306Spider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(QQSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(MainMainSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(ShiJiJiaYuanSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(WeiXinSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(ZhiHuSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
			pushTaskToQueue(PapaTask.builder()
					.telephone(username).id(id)
					.papaClz(YouZanSpider.class.getName())
					.name(name)
					.idcard(idcard).build());
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
