package com.jisucloud.clawler.regagent.service.impl.shop;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.PersistenceCookieJar;
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
//@Component
public class HuaWeiSpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;

	@Override
	public String message() {
		return "本来生活网,自建仓储物流,冷链配送,全部食品严格把关,经过安全检测,品质保证,48小时退换货,提供进口水果,应季水果,有机蔬菜,牛羊肉,粮油,褚橙,红酒,车厘子等.";
	}

	@Override
	public String platform() {
		return "benlai";
	}

	@Override
	public String home() {
		return "benlai.com";
	}

	@Override
	public String platformName() {
		return "本来生活网";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("电商", new String[] { });
			}
		};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new HuaWeiSpider().checkTelephone("18210538513"));
		System.out.println(new HuaWeiSpider().checkTelephone("18210538511"));
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			chromeDriver.quicklyVisit("http://www.baidu.com/link?url=3AJLrfNi_Lpqo2XYpboeZuCFZnKk8VqIl8ma8RSrCQq&wd=&eqid=c208d761001ba6ed000000025cf65012");
			Thread.sleep(2000);
			chromeDriver.quicklyVisit("https://hwid1.vmall.com/AMW/portal/resetPwd/forgetbyid.html?reqClientType=26&loginChannel=26000000&countryCode=cn&loginUrl=https%3A%2F%2Fhwid1.vmall.com%2FCAS%2Fportal%2Flogin.html&service=https%3A%2F%2Fwww.vmall.com%2Faccount%2Fcaslogin&lang=zh-cn&themeName=red");
			Thread.sleep(2000);
			chromeDriver.findElementById("formBean_username").sendKeys(account);
			chromeDriver.findElementById("btnSubmit").click();
			Thread.sleep(5000);
			String res = chromeDriver.getPageSource();
			if (res.contains("不存在")) {
				return false;
			}else if (res.contains("现在可接收短信")) {
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
