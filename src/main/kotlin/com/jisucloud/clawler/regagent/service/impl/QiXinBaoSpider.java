package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.PersistenceCookieJar;
import com.jisucloud.clawler.regagent.util.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
//@Component
public class QiXinBaoSpider implements PapaSpider {

	private ChromeDriver chromeDriver;
	private PersistenceCookieJar persistenceCookieJar = new PersistenceCookieJar();
	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.cookieJar(persistenceCookieJar)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "启信宝为您提供全国企业信用信息查询服务,包括企业注册信息查询、企业工商信息查询、企业信用查询、企业信息查询等相关信息查询!还可以深入了解企业相关的法人股东,企业。";
	}

	@Override
	public String platform() {
		return "qixin";
	}

	@Override
	public String home() {
		return "qixin.com";
	}

	@Override
	public String platformName() {
		return "启信宝";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("工具" , new String[] { });
			}
		};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new QiXinBaoSpider().checkTelephone("18210538000"));
		System.out.println(new QiXinBaoSpider().checkTelephone("18210538513"));
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
			chromeDriver.get("https://www.qixin.com/auth/login?return_url=%2F");
			Thread.sleep(5000);
			persistenceCookieJar.saveCookies(chromeDriver.getCurrentUrl(), chromeDriver.manage().getCookies());
			String url = "https://www.qixin.com/api/user/login";
			JSONObject body = new JSONObject();
			body.put("acc", account);
			body.put("pass", "U2FsdGVkX19UJlnXbTkqw+ALLI7sWM2NslGTQGfgHYI=");
			JSONObject captcha = new JSONObject();
			captcha.put("isTrusted", true);
			body.put("captcha", captcha);
			body.put("keepLogin", true);
	        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
	                , body.toJSONString());
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qixin.com")
					.addHeader("Referer", "https://www.qixin.com/auth/login?return_url=%2F")
					.post(requestBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			log.info(res);
			if (res.contains("密码错误")) {
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
