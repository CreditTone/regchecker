package com.jisucloud.clawler.regagent.service.impl.trip;

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
public class QingMangGuoSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "青芒果旅行网WAP版本为您提供全国1000个城市,超过20,000家青年旅舍,经济型酒店,酒店式公寓,宾馆,客栈,家庭旅馆,招待所的旅游预订,查询和点评。";
	}

	@Override
	public String platform() {
		return "qmango";
	}

	@Override
	public String home() {
		return "qmango.com";
	}

	@Override
	public String platformName() {
		return "青芒果旅行";
	}

	@Override
	public String[] tags() {
		return new String[] {"旅游" , "酒店" , "o2o"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new QingMangGuoSpider().checkTelephone("13910252045"));
//		System.out.println(new QingMangGuoSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.qmango.com/users/ajax/checkInfo.asp?_="+System.currentTimeMillis()+"&t=m&v=" + account;
			FormBody formBody = new FormBody
	                .Builder()
	                .add("regMobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qmango.com")
					.addHeader("Referer", "http://www.qmango.com/users/login.asp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("1")) {
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
