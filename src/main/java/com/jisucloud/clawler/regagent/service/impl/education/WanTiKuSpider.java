package com.jisucloud.clawler.regagent.service.impl.education;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "wantiku.com", 
		message = "万题库,拥有名师视频解析的智能题库!考证通关大杀器,科学通关,懒人必备!... 智能辅导 互动式欢乐直播新大纲名师课程 智能练习 利用人工智能算法实现一对一智能出题。", 
		platform = "wantiku", 
		platformName = "万题库", 
		tags = { "考试","学习","教育" }, 
		testTelephones = { "15970663703", "18212345678" })
public class WanTiKuSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			String url = "http://www.wantiku.com/login/";
			chromeDriver.get(url);smartSleep(3000);
			chromeDriver.findElementByCssSelector("input[name=UserName]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[name=UserPass]").sendKeys("dsdsk92812ddddv");
			chromeDriver.findElementByLinkText("登录").click();smartSleep(3000);
			Document doc = Jsoup.parse(chromeDriver.getPageSource());
			if (doc.select("div.tishi_wrong.fl.handle-pwd").text().contains("密码错误")) {
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
