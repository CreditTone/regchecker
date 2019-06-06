package com.jisucloud.clawler.regagent.service.impl.education;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WanTiKuSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "万题库,拥有名师视频解析的智能题库!考证通关大杀器,科学通关,懒人必备!... 智能辅导 互动式欢乐直播新大纲名师课程 智能练习 利用人工智能算法实现一对一智能出题。";
	}

	@Override
	public String platform() {
		return "wantiku";
	}

	@Override
	public String home() {
		return "wantiku.com";
	}

	@Override
	public String platformName() {
		return "万题库";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("金融", new String[] { "储蓄"});
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new WanTiKuSpider().checkTelephone("15970663703"));
//		System.out.println(new WanTiKuSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			String url = "http://www.wantiku.com/login/";
			chromeDriver.get(url);
			Thread.sleep(3000);
			chromeDriver.findElementByCssSelector("input[name=UserName]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[name=UserPass]").sendKeys("dsdsk92812ddddv");
			chromeDriver.findElementByLinkText("登录").click();
			Thread.sleep(3000);
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
