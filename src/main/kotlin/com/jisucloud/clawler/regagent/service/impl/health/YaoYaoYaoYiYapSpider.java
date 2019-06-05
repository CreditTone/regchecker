package com.jisucloud.clawler.regagent.service.impl.health;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class YaoYaoYaoYiYapSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "111医药馆是德生堂旗下网上药店，专做药品特卖的网站。111医药馆网上药店是国家药监局认证专业网上药店，经营药品种类齐全，网上购药就选111医药馆网上药店。";
	}

	@Override
	public String platform() {
		return "111yao";
	}

	@Override
	public String home() {
		return "111yao.com";
	}

	@Override
	public String platformName() {
		return "111医药";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("购物", new String[] { });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new YaoYaoYaoYiYapSpider().checkTelephone("13844441111"));
//		System.out.println(new YaoYaoYaoYiYapSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.111yao.com/login/login!userLogin.action?0.8448281925529483";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("password", "wocaonimabi")
	                .add("phoneCode", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.111yao.com")
					.addHeader("Referer", "http://www.111yao.com/login/login!index.action")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getIntValue("flag") == 1) {
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
