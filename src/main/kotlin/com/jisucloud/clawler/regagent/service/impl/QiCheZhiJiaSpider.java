package com.jisucloud.clawler.regagent.service.impl;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class QiCheZhiJiaSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "汽车之家为您提供最新汽车报价，汽车图片，汽车价格大全，最精彩的汽车新闻、行情、评测、导购内容，是提供信息最快最全的中国汽车网站。";
	}

	@Override
	public String platform() {
		return "autohome";
	}

	@Override
	public String home() {
		return "autohome.com";
	}

	@Override
	public String platformName() {
		return "汽车之家";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("生活", new String[] {"汽车" });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new QiCheZhiJiaSpider().checkTelephone("18210538500"));
//		System.out.println(new QiCheZhiJiaSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.autohome.com.cn/AccountApi/CheckPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("isOverSea", "0")
	                .add("validcodetype", "1")
	                .add("", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.autohome.com.cn")
					.addHeader("Referer", "https://account.autohome.com.cn/register?backurl=https%253A%252F%252Fwww.autohome.com.cn%252Fbeijing%252F&fPosition=10001&sPosition=1000100&platform=1&pvareaid=3311227")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("MobileExist")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
