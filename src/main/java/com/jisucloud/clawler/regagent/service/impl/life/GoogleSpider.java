package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "google.com", 
		message = "谷歌是一家位于美国的跨国科技企业，业务包括互联网搜索、云计算、广告技术等，同时开发并提供大量基于互联网的产品与服务，其主要利润来自于AdWords等广告服务，被公认为全球最大的搜索引擎公司。", 
		platform = "google", 
		platformName = "谷歌", 
		tags = {"软件服务" , "搜索引擎" }, 
		testTelephones = {"13910002005", "18212345678" })
public class GoogleSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithGoogleProxy(true, false, IOS_USER_AGENT);
			chromeDriver.get("https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fwww.youtube.com%2Fsignin%3Faction_handle_signin%3Dtrue%26app%3Ddesktop%26hl%3Dzh-CN%26next%3D%252F&hl=zh-CN&service=youtube&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
			smartSleep(1000);
			chromeDriver.findElementById("identifierId").sendKeys("+86"+account);
			chromeDriver.findElementByCssSelector("span[class='RveJvd snByac']").click();
			smartSleep(60000);
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
