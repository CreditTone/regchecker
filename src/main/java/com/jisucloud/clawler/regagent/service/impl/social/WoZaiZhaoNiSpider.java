package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;


import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "95195.com", 
		message = "我在找你-相亲交友,完全免费!所有认证会员都经过实名制核实!为亿万单身男女提供完全免费、真实可信、简便高效的相亲交友聊天平台!", 
		platform = "95195", 
		platformName = "我在找你", 
		tags = { "单身交友" }, 
		testTelephones = { "18810038000", "18210538513" })
public class WoZaiZhaoNiSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;


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
