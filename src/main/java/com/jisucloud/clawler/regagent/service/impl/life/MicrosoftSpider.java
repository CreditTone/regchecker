package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class MicrosoftSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "微软，是一家美国跨国科技公司，也是世界PC（Personal Computer，个人计算机）软件开发的先导，由比尔·盖茨与保罗·艾伦创办于1975年，公司总部设立在华盛顿州的雷德蒙德。";
	}

	@Override
	public String platform() {
		return "microsoft";
	}

	@Override
	public String home() {
		return "microsoft.com";
	}

	@Override
	public String platformName() {
		return "微软";
	}

	@Override
	public String[] tags() {
		return new String[] {"软件服务" , "系统工具"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imgObj");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	String code;

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, false, null);
			chromeDriver.get("https://signup.live.com/signup?wa=wsignin1.0&rpsnv=13&rver=7.1.6819.0&wp=MBI_SSL&wreply=https%3a%2f%2fwww.microsoft.com%2fzh-cn%2f&id=74335&aadredir=1&contextid=5F08DA5E184AF607&bk=1561470130&uiflavor=web&mkt=ZH-CN&lc=2052&uaid=378dc6e56f51442ddf271bc827c9d1f9&lic=1");
			chromeDriver.findElementById("phoneSwitch").click();smartSleep(1000);
			chromeDriver.findElementById("MemberName").sendKeys(account);
			chromeDriver.findElementByCssSelector("img.logo").click();smartSleep(3000);
			if (chromeDriver.checkElement("#MemberNameError")) {
				return true;
			}
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
