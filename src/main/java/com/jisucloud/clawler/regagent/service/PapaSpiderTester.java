package com.jisucloud.clawler.regagent.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebElement;

import com.deep007.spiderbase.util.JEmail.JEmailBuilder;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.MitmServer;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.RegAgentApplication;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.impl._3c.*;
import com.jisucloud.clawler.regagent.service.impl.borrow.*;
import com.jisucloud.clawler.regagent.service.impl.car.*;
import com.jisucloud.clawler.regagent.service.impl.education.*;
import com.jisucloud.clawler.regagent.service.impl.email.*;
import com.jisucloud.clawler.regagent.service.impl.game.*;
import com.jisucloud.clawler.regagent.service.impl.health.*;
import com.jisucloud.clawler.regagent.service.impl.law.*;
import com.jisucloud.clawler.regagent.service.impl.life.*;
import com.jisucloud.clawler.regagent.service.impl.money.*;
import com.jisucloud.clawler.regagent.service.impl.music.*;
import com.jisucloud.clawler.regagent.service.impl.news.*;
import com.jisucloud.clawler.regagent.service.impl.pay.*;
import com.jisucloud.clawler.regagent.service.impl.photo.*;
import com.jisucloud.clawler.regagent.service.impl.reader.*;
import com.jisucloud.clawler.regagent.service.impl.saas.*;
import com.jisucloud.clawler.regagent.service.impl.shop.*;
import com.jisucloud.clawler.regagent.service.impl.social.*;
import com.jisucloud.clawler.regagent.service.impl.software.*;
import com.jisucloud.clawler.regagent.service.impl.trip.*;
import com.jisucloud.clawler.regagent.service.impl.util.*;
import com.jisucloud.clawler.regagent.service.impl.video.*;
import com.jisucloud.clawler.regagent.service.impl.work.*;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unused")
@Slf4j
public class PapaSpiderTester {

	static {
		RegAgentApplication.init();
	}

	public static interface PapaSpiderTestListener {

		public void testSuccess(Class<? extends PapaSpider> clz);

		public void testFailure(Class<? extends PapaSpider> clz);

	}

	public static void testing(Set<Class<? extends PapaSpider>> papaSpiders,
			PapaSpiderTestListener papaSpiderTestListener, JEmailBuilder emailBuilder) {
		for (Iterator<Class<? extends PapaSpider>> iterator = papaSpiders.iterator(); iterator.hasNext();) {
			Class<? extends PapaSpider> clz = iterator.next();
			boolean success = false;
			try {
				success = testing(clz);
			} catch (Exception e) {
				if (emailBuilder != null) {
					emailBuilder.addContentLine("PapaSpiderTester:测试" + clz.getName() + "异常");
				}
				log.warn("测试" + clz.getName() + "异常", e);
			} finally {
				if (success) {
					if (emailBuilder != null) {
						emailBuilder.addContentLine("PapaSpiderTester:" + clz.getName() + "测试成功");
					}
					papaSpiderTestListener.testSuccess(clz);
				} else {
					if (emailBuilder != null) {
						emailBuilder.addContentLine("PapaSpiderTester:" + clz.getName() + "测试失败");
					}
					papaSpiderTestListener.testFailure(clz);
				}
			}
		}
	}

	public static void testing(Set<Class<? extends PapaSpider>> papaSpiders,
			PapaSpiderTestListener papaSpiderTestListener) {
		testing(papaSpiders, papaSpiderTestListener, null);
	}

	@SuppressWarnings("deprecation")
	public static boolean testing(Class<? extends PapaSpider> clz) throws Exception {
		PapaSpiderConfig papaSpiderConfig = clz.getAnnotation(PapaSpiderConfig.class);
		String[] testTels = papaSpiderConfig.testTelephones();
		if (testTels == null || testTels.length < 2) {
			throw new RuntimeException("无法测试，" + clz.getName() + " 最低需要两个不同的比较号码。一个确认已经注册，一个确认没有注册。");
		}
		// 如果全为true或者全为false，证明测试失败
		int trueCount = 0;
		int falseCount = 0;
		PapaSpider instance = null;
		for (int i = 0 ; i < testTels.length ; i ++) {
			String tel = testTels[i];
			instance = clz.newInstance();
			if (instance.checkTelephone(tel)) {
				log.info(tel + "已注册" + papaSpiderConfig.platformName());
				trueCount++;
			} else {
				log.info(tel + "未注册" + papaSpiderConfig.platformName());
				falseCount++;
			}
		}
		return (trueCount != 0 && falseCount != 0);
	}

	/**
	 * 手工测试专用
	 * 
	 * @param clz
	 */
	public static void testingWithPrint(Class<? extends PapaSpider> clz) {
		boolean success = false;
		try {
			success = testing(clz);
		} catch (Exception e) {
			log.warn("测试" + clz.getName() + "异常", e);
		} finally {
			if (success) {
				log.info("测试成功:" + clz.getName());
			} else {
				log.info("测试失败:" + clz.getName());
			}
			MitmServer.getInstance().stop();
		}
	}

	public static void main(String[] args) throws Exception {
		testingWithPrint(_168JinFuSpider.class);
	}
		
}
