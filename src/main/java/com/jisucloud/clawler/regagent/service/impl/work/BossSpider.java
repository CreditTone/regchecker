package com.jisucloud.clawler.regagent.service.impl.work;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;

@Slf4j
//@UsePapaSpider
public class BossSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;
	
	private boolean checkTelephone = false;
	
	//暂时不能访问此页面，被反扒
	public boolean noSpider = false;

	@Override
	public String message() {
		return "BOSS直聘是权威领先的招聘网,开启人才网招聘求职新时代,让求职者与Boss直接开聊、加快面试、即时反馈,找工作就来BOSS直聘和Boss开聊吧。";
	}

	@Override
	public String platform() {
		return "zhipin";
	}

	@Override
	public String home() {
		return "zhipin.com";
	}

	@Override
	public String platformName() {
		return "BOSS直聘";
	}

	@Override
	public String[] tags() {
		return new String[] {"求职", "招聘" ,"互联网招聘"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "18800000001");
	}
	
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(BossSpider.class);
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(false, false, CHROME_USER_AGENT);smartSleep(3000);
			for (int i = 0; i < 3; i++) {
				chromeDriver.get("https://login.zhipin.com/?ka=header-login");smartSleep(RANDOM.nextInt(5000));
				WebElement accName = chromeDriver.findElementByCssSelector("input[name='account']");
				chromeDriver.keyboardInput(accName, account);
				WebElement accPass = chromeDriver.findElementByCssSelector("input[name='password']");
				chromeDriver.keyboardInput(accPass, "1da0210x");
//smartSleep(60000);
				WebElement rdsSlideReset = chromeDriver.findElementByCssSelector("div[class='nc_wrapper']");
				WebElement rdsSlideBtn = chromeDriver.findElementByCssSelector("span[class='nc_iconfont btn_slide']");
				chromeDriver.switchSlide(rdsSlideReset, rdsSlideBtn);smartSleep(RANDOM.nextInt(3000));
				WebElement next = chromeDriver.findElementByCssSelector("button[type='submit']");
				chromeDriver.mouseClick(next);smartSleep(RANDOM.nextInt(8000) + 5000);
				break;
//				if (chromeDriver.checkElement("div.alipay-xbox")) {
//					return true;
//				}
//				String currentUrl = chromeDriver.getCurrentUrl();
//				System.out.println(currentUrl);
//				String pageSource = chromeDriver.getPageSource();
//				if (currentUrl.contains("queryStrategy.htm?")) {
//					return true;
//				}else if (pageSource.contains("该账户不存在，请重新输入")) {
//					return false;
//				}else if (pageSource.contains("暂时不能访问此页面")) {
//					noSpider = true;
//					return false;
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTelephone;
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
