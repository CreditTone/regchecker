package com.jisucloud.clawler.regagent.service.impl;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class QianChengWuYouSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "安卓市场,Android,安卓,安智市场-国内最专业的Android安卓手机电子市场，提供海量安卓软件、Android手机游戏、安卓最新汉化软件资源及最新APK汉化、汉化破解APP、APK免费下载，致力于为用户打造最贴心的Android安卓应用商店。";
	}

	@Override
	public String platform() {
		return "anzhi";
	}

	@Override
	public String home() {
		return "anzhi.com";
	}

	@Override
	public String platformName() {
		return "安智市场";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("生活", new String[] { "app市场" });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new QianChengWuYouSpider().checkTelephone("18210014444"));
//		System.out.println(new QianChengWuYouSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://login.51job.com/ajax/checkinfo.php?jsoncallback=jQuery18309636398222161634_"+System.currentTimeMillis()+"&value="+account+"&nation=CN&type=mobile&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "login.51job.com")
					.addHeader("Referer", "https://login.51job.com/register.php?lang=c&from_domain=i&source=&isjump=0&url=")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("result\":1")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
