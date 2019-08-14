package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.HashMap;
import java.util.Map;


@PapaSpiderConfig(home = "kyxdloan.com", message = "速借贷作为一款应用，解决的是需求用户贷款难的问题，我们用专业的服务，解决您贷款难、审核严、下款慢、利息高的问题！！！", platform = "sujiedai", platformName = "速借贷", tags = {
		"P2P", "消费分期", "借贷" }, testTelephones = { "15985268900", "18210538513" })
public class SujieDaiSpider extends PapaSpider {

	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Sign", "EDCB7FAA0AC96CECA3652AE07E4503840FE04910");
		headers.put("Host", "api.kyxdloan.com");
		headers.put("User-Agent",
				"deviceToken:7121d2f6f3a817f98e74e62172733877, client:android, version:1.0.7, build:181207, channel:wandoujia, userId:, screenSize:480x800,safeToken:904ef22b584147d5650c1f79a64354aa,Fastloan:1.0.7,appName:fastloan");
		return Headers.of(headers);
	}

	private FormBody getParams(String mobile) {
		FormBody formBody = new FormBody.Builder().add("u", mobile).add("isremember", "false")
				.add("deviceType", "Android").add("deviceToken", "7121d2f6f3a817f98e74e62172733877")
				.add("p", "24ffc00d04bc540d8cb473d5d37b22fa").add("version", "1.0.7")
				.add("timestamp", System.currentTimeMillis() + "").build();
		return formBody;
	}

	public boolean checkTelephone(String account) {
		String url = "https://api.kyxdloan.com/user/signin_check";
		try {
			Request request = new Request.Builder().url(url).headers(getHeader()).post(getParams(account)).build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("moreInfo").contains("密码错误")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return checkTelephone(account);
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
