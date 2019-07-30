package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;


import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class WoZaiZhaoNiSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;


	@Override
	public String message() {
		return "我在找你-相亲交友,完全免费!所有认证会员都经过实名制核实!为亿万单身男女提供完全免费、真实可信、简便高效的相亲交友聊天平台!";
	}

	@Override
	public String platform() {
		return "95195";
	}

	@Override
	public String home() {
		return "95195.com";
	}

	@Override
	public String platformName() {
		return "我在找你";
	}

	@Override
	public String[] tags() {
		return new String[] {"单身交友"};
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18810038000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("http://www.95195.com");
			chromeDriver.get("http://www.95195.com/user/reg/");
			chromeDriver.findElementById("r_mobile").sendKeys(account);
			chromeDriver.findElementById("nickname").click();smartSleep(2000);
			String text = chromeDriver.findElementById("mobile_explain").getText();
			return text.contains("已经注册");
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
