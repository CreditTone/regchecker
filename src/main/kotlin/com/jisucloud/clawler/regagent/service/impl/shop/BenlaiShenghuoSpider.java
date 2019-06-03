package com.jisucloud.clawler.regagent.service.impl.shop;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.PersistenceCookieJar;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
public class BenlaiShenghuoSpider implements PapaSpider {
	
	private ChromeDriver chromeDriver;
	private PersistenceCookieJar persistenceCookieJar = new PersistenceCookieJar();
	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.cookieJar(persistenceCookieJar)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

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

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new BenlaiShenghuoSpider().checkTelephone("13879691485"));
//		System.out.println(new BenlaiShenghuoSpider().checkTelephone("18210538513"));
//		System.out.println(new BenlaiShenghuoSpider().checkTelephone("18210538511"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
			chromeDriver.get("https://m.benlai.com/showlogin?otherLogin=1&loginType=2&backType=&comeFromApp=0&afterUrl=");
			Thread.sleep(2000);
			chromeDriver.findElementById("customerID").sendKeys(account);
			chromeDriver.findElementById("customerPwd").sendKeys("woxiaoxoa132");
			chromeDriver.findElementById("loginBtn").click();
			Thread.sleep(3000);
			Document doc = Jsoup.parse(chromeDriver.getPageSource());
			String res = doc.select("#baseErrorMsg").text();
			if (res.contains("用户名不存在")) {
				return false;
			}else if (res.contains("验证码输入错误") || res.contains("密码")) {
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
