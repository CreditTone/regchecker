package com.jisucloud.clawler.regagent.service.impl.software;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "microsoft.com", 
		message = "微软，是一家美国跨国科技公司，也是世界PC（Personal Computer，个人计算机）软件开发的先导，由比尔·盖茨与保罗·艾伦创办于1975年，公司总部设立在华盛顿州的雷德蒙德。", 
		platform = "microsoft", 
		platformName = "微软", 
		tags = { "软件服务" , "系统工具" }, 
		testTelephones = { "13912345678", "18212345678" })
public class MicrosoftSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, null);
			chromeDriver.get("https://signup.live.com/signup?wa=wsignin1.0&rpsnv=13&rver=7.1.6819.0&wp=MBI_SSL&wreply=https%3a%2f%2fwww.microsoft.com%2fzh-cn%2f&id=74335&aadredir=1&contextid=5F08DA5E184AF607&bk=1561470130&uiflavor=web&mkt=ZH-CN&lc=2052&uaid=378dc6e56f51442ddf271bc827c9d1f9&lic=1");
			chromeDriver.findElementById("phoneSwitch").click();
			smartSleep(1000);
			chromeDriver.findElementById("MemberName").sendKeys(account);
			chromeDriver.findElementByCssSelector("#iSignupAction").click();
			smartSleep(3000);
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
