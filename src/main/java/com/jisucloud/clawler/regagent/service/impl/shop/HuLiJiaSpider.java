package com.jisucloud.clawler.regagent.service.impl.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "helijia.com", 
		message = "低价美甲，美容，美发，造型等，品质上门服务；让闺蜜都惊讶的效果！河狸家是千万女性信赖的“美甲、美容、美发、微整形平台”，致力于为所有爱美女性提供高品质的上门服务！", 
		platform = "helijia", 
		platformName = "河狸家", 
		tags = { "购物" , "低价" , "美容", "女性" }, 
		testTelephones = { "13654022846", "18210538513" })
public class HuLiJiaSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://user.helijia.com/app-user-center/user/loginBeforeInfo?mobile="+account+"&t=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.helijia.com/#")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			JSONObject data = JSON.parseObject(res).getJSONObject("data");
			return data.getBooleanValue("registered");
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
