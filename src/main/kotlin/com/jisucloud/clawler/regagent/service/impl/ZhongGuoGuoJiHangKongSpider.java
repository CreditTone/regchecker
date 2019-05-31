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
public class ZhongGuoGuoJiHangKongSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "中国国际航空股份有限公司官网，提供国际国内飞机票查询、航班查询、特价打折机票预订服务。";
	}

	@Override
	public String platform() {
		return "airchina";
	}

	@Override
	public String home() {
		return "airchina.com";
	}

	@Override
	public String platformName() {
		return "中国国际航空";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("出行", new String[] { "飞机" });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhongGuoGuoJiHangKongSpider().checkTelephone("13800000000"));
//		System.out.println(new ZhongGuoGuoJiHangKongSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.airchina.com.cn/www/servlet/com.ace.um.userRegister.servlet.PhoneValidator";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("shouji",account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.airchina.com.cn")
					.addHeader("Referer", "http://www.airchina.com.cn/www/jsp/userManager/register.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
