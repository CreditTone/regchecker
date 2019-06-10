package com.jisucloud.clawler.regagent.service.impl.money;

import com.jisucloud.clawler.regagent.http.PersistenceCookieJar;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WuKongLiCaiSpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;

	@Override
	public String message() {
		return "悟空理财,玖富旗下品牌,玖富金融理财大师兄,专注为用户提供多元的出借选择和优质的出借服务。月账户、季账户、年账户等计划,满足各种资金的出借需求。";
	}

	@Override
	public String platform() {
		return "wukonglicai";
	}

	@Override
	public String home() {
		return "wukonglicai.com";
	}

	@Override
	public String platformName() {
		return "悟空理财";
	}

	@Override
	public String[] tags() {
		return new String[] {"理财"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new WuKongLiCaiSpider().checkTelephone("13910252045"));
//		System.out.println(new WuKongLiCaiSpider().checkTelephone("13910252040"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.get("https://zhifu.wukonglicai.com//cphandle/login.htm");
			Thread.sleep(2000);
			chromeDriver.findElementByCssSelector("input[name=mobile]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[name=password]").sendKeys("casda12e12d");
			chromeDriver.findElementById("bt").click();
			Thread.sleep(3000);
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
