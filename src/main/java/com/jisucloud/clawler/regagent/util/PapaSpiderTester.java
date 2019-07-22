package com.jisucloud.clawler.regagent.util;

import java.util.Iterator;
import java.util.Set;

import com.deep077.spiderbase.selenium.mitm.MitmServer;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.service.impl.borrow.*;
import com.jisucloud.clawler.regagent.service.impl.education.*;
import com.jisucloud.clawler.regagent.service.impl.email.*;
import com.jisucloud.clawler.regagent.service.impl.game.*;
import com.jisucloud.clawler.regagent.service.impl.health.*;
import com.jisucloud.clawler.regagent.service.impl.life.*;
import com.jisucloud.clawler.regagent.service.impl.money.*;
import com.jisucloud.clawler.regagent.service.impl.shop.*;
import com.jisucloud.clawler.regagent.service.impl.social.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PapaSpiderTester {

	public static interface PapaSpiderTestListener {
		
		public void testSuccess(Class<? extends PapaSpider> clz);
		
		public void testFailure(Class<? extends PapaSpider> clz);
		
	}
	
	public static void testing(Set<Class<? extends PapaSpider>> papaSpiders, PapaSpiderTestListener papaSpiderTestListener) {
		for (Iterator<Class<? extends PapaSpider>> iterator = papaSpiders.iterator(); iterator.hasNext();) {
			Class<? extends PapaSpider> clz = iterator.next();
			log.debug("开始测试:"+clz.getName());
			TimerRecoder timerRecoder = new TimerRecoder().start();
			boolean success = false;
			try {
				PapaSpider instance =  clz.newInstance();
				Set<String> testTels = instance.getTestTelephones();
				if (testTels == null || testTels.size() < 2) {
					log.warn("无法测试，"+clz.getName()+" 最低需要两个不同的比较号码。一个确认已经注册，一个确认没有注册。");
					continue;
				}
				//如果全为true或者全为false，证明测试失败
				int trueCount = 0;
				int falseCount = 0;
				for (Iterator<String> iterator2 = testTels.iterator(); iterator2.hasNext();) {
					String tel = iterator2.next();
					if (instance.checkTelephone(tel)) {
						trueCount ++;
					}else {
						falseCount ++;
					}
					if (iterator2.hasNext()) {
						instance =  clz.newInstance();
					}
				}
				success = (trueCount != 0 && falseCount != 0);
			} catch (Exception e) {
				log.warn("测试"+clz.getName()+"异常", e);
			}finally {
				if (success) {
					log.debug("测试成功:"+clz.getName()+",用时"+timerRecoder.getText());
					papaSpiderTestListener.testSuccess(clz);
				}else {
					log.warn("测试失败:"+clz.getName()+",用时"+timerRecoder.getText());
					papaSpiderTestListener.testFailure(clz);
				}
				
			}
		}
	}
	
	/**
	 * 手工测试专用
	 * @param clz
	 */
	public static void testingWithPrint(Class<? extends PapaSpider> clz) {
		boolean success = false;
		try {
			PapaSpider instance =  clz.newInstance();
			Set<String> testTels = instance.getTestTelephones();
			if (testTels == null || testTels.size() < 2) {
				log.warn("无法测试，"+clz.getName()+" 最低需要两个不同的比较号码。一个确认已经注册，一个确认没有注册。");
				return;
			}
			//如果全为true或者全为false，证明测试失败
			int trueCount = 0;
			int falseCount = 0;
			for (Iterator<String> iterator2 = testTels.iterator(); iterator2.hasNext();) {
				String tel = iterator2.next();
				if (instance.checkTelephone(tel)) {
					log.info(tel+"已注册"+instance.platformName());
					trueCount ++;
				}else {
					log.info(tel+"未注册"+instance.platformName());
					falseCount ++;
				}
				if (iterator2.hasNext()) {
					instance =  clz.newInstance();
				}
			}
			success = (trueCount != 0 && falseCount != 0);
		} catch (Exception e) {
			log.warn("测试"+clz.getName()+"异常", e);
		}finally {
			if (success) {
				log.info("测试成功:"+clz.getName());
			}else {
				log.info("测试失败:"+clz.getName());
			}
			MitmServer.getInstance().stop();
		}
	}
	
	public static void main(String[] args) {
//		testingWithPrint(NiWoDaiSpider.class);
//		testingWithPrint(XiaoYingKaDaiService.class);
//		testingWithPrint(HengYiDaiService.class);
//		testingWithPrint(LianZiDaiSpider.class);
//		testingWithPrint(MinDaiService.class);
//		testingWithPrint(PharmacySpider.class);
//		testingWithPrint(WangdaiZhijiaSpider.class);
//		testingWithPrint(XYSpider.class);
//		testingWithPrint(ShangDeSpider.class);
//		testingWithPrint(TianNaSpider.class);
//		testingWithPrint(YiXinHuiMinSpider.class);
//		testingWithPrint(JuRenSpider.class);
//		testingWithPrint(BaiheWangSpider.class);
//		testingWithPrint(JiMuService.class);
//		testingWithPrint(DangDangWangSpider.class);
//		testingWithPrint(BoJinDaiService.class);
//		testingWithPrint(LanCaiWangSpider.class);
//		testingWithPrint(YiDaiWangSpider.class);
//		testingWithPrint(QiHu360Spider.class);
//		testingWithPrint(_2345DaiKuanWangSpider.class);
//		testingWithPrint(ENet126EmailSpider.class);
	}
}
