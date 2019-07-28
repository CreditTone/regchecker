package com.jisucloud.clawler.regagent.service.impl.social;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class LinkedinSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "领英,在全球领先职业社交平台查看英领的职业档案。英领的职业档案列出了教育经历。查看英领的完整档案,结识职场人脉和查看相似公司的职位。";
	}

	@Override
	public String platform() {
		return "linkedin";
	}

	@Override
	public String home() {
		return "linkedin.com";
	}

	@Override
	public String platformName() {
		return "领英";
	}

	@Override
	public String[] tags() {
		return new String[] {"招聘" , "职场" , "人脉"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268900", "18210538513");
	}

	public static void main(String[] args) throws InterruptedException {
		PapaSpiderTester.testingWithPrint(LinkedinSpider.class);
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(true, false);
			chromeDriver.get("https://www.linkedin.com/uas/login?session_redirect=https%3A%2F%2Fcn%2Elinkedin%2Ecom%2Fin%2F%25E9%25A2%2586-%25E8%258B%25B1-5783b911a&amp;fromSignIn=true&trk=nav_header_signin");smartSleep(1000);
			chromeDriver.findElementById("username").sendKeys("+86"+account);
			chromeDriver.findElementById("password").sendKeys("dx1mxa1la9");
			chromeDriver.findElementByCssSelector("button[type='submit']").click();smartSleep(3000);
			return chromeDriver.checkElement("#error-for-password");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTel;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
