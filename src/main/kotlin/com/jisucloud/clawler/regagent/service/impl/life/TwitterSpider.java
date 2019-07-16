package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
//@UsePapaSpider
public class TwitterSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "Twitter（推特）是一家美国社交网络及微博客服务的网站，是全球互联网上访问量最大的十个网站之一。是微博客的典型应用。它可以让用户更新不超过140个字符的消息，这些消息也被称作“推文（Tweet）”。";
	}

	@Override
	public String platform() {
		return "twitter";
	}

	@Override
	public String home() {
		return "twitter.com";
	}

	@Override
	public String platformName() {
		return "美国在线";
	}

	@Override
	public String[] tags() {
		return new String[] {"新闻" , "国外新闻"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910002005", "18210538513");
	}
	

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithGoogleProxy(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://twitter.com/account/begin_password_reset?lang=zh-cn");
			smartSleep(2000);
			chromeDriver.findElementById("username").sendKeys("+86"+account);
			chromeDriver.findElementByCssSelector("button[type='submit']").click();
			smartSleep(3000);
//			String uiHeaderTitle = chromeDriver.findElementByCssSelector("h2[class='uiHeaderTitle']").getText();
//			return uiHeaderTitle.contains("重置密码");
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
