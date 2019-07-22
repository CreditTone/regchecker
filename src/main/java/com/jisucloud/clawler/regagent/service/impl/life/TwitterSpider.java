package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

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
		return "Twitter";
	}

	@Override
	public String[] tags() {
		return new String[] {"社交" , "媒体", "微博"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910002005", "18210538513");
	}
	

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithGoogleProxy(true, false, CHROME_USER_AGENT);
			chromeDriver.get("https://twitter.com/account/begin_password_reset?lang=zh-cn");
			smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[name='account_identifier']").sendKeys("+86"+account);
			chromeDriver.findElementByCssSelector("input[value='搜索']").click();
			smartSleep(3000);
			if (chromeDriver.checkElement("div[class='PageHeader is-errored']")) {
				String uiHeaderTitle = chromeDriver.findElementByCssSelector("div[class='PageHeader is-errored']").getText();
				if (uiHeaderTitle.contains("关联了多个账号")) {
					return true;
				}
				return !uiHeaderTitle.contains("无法使用该信息搜索你的账号");
			}
			return false;
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
