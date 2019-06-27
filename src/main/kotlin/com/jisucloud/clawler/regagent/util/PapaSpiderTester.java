package com.jisucloud.clawler.regagent.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PapaSpiderTester {

	public static interface PapaSpiderTestListener {
		
		public void testSuccess(Class<? extends PapaSpider> clz);
		
		public void testFailure(Class<? extends PapaSpider> clz);
		
	}
	
	public void testing(Set<Class<? extends PapaSpider>> papaSpiders, PapaSpiderTestListener papaSpiderTestListener) {
		for (Iterator<Class<? extends PapaSpider>> iterator = papaSpiders.iterator(); iterator.hasNext();) {
			Class<? extends PapaSpider> clz = iterator.next();
			boolean success = false;
			try {
				PapaSpider instance =  clz.newInstance();
				Set<String> testTels = new HashSet<>();
				if (testTels.size() < 2) {
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
				}
				success = (trueCount != 0 && falseCount != 0);
			} catch (Exception e) {
				log.warn("测试"+clz.getName()+"异常", e);
			}finally {
				if (success) {
					papaSpiderTestListener.testSuccess(clz);
				}else {
					papaSpiderTestListener.testFailure(clz);
				}
			}
		}
	}
}
