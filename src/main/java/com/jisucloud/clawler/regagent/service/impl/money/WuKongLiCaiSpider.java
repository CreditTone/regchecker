package com.jisucloud.clawler.regagent.service.impl.money;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@PapaSpiderConfig(
		home = "wukonglicai.com", 
		message = "玖富金融,玖富旗下品牌,玖富金融理财大师兄,专注为用户提供多元的出借选择和优质的出借服务。月账户、季账户、年账户等计划,满足各种资金的出借需求。", 
		platform = "wukonglicai", 
		platformName = "玖富金融", 
		tags = { "理财" , "p2p" , "借贷" }, 
		testTelephones = { "15985268900", "18210538513" })
public class WuKongLiCaiSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://zhifu.wukonglicai.com//cphandle/login.htm");
			smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[name=mobile]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[name=password]").sendKeys("casda12e12d");
			chromeDriver.findElementById("bt").click();
			smartSleep(3000);
			Document doc = Jsoup.parse(chromeDriver.getPageSource());
			String res = doc.select("#message").text();
			if (res.contains("密码不正确")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return false;
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
