package com.jisucloud.clawler.regagent.service.impl.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class HuLiJiaSpider extends PapaSpider {

	@Override
	public String message() {
		return "低价美甲，美容，美发，造型等，品质上门服务；让闺蜜都惊讶的效果！河狸家是千万女性信赖的“美甲、美容、美发、微整形平台”，致力于为所有爱美女性提供高品质的上门服务！";
	}

	@Override
	public String platform() {
		return "helijia";
	}

	@Override
	public String home() {
		return "helijia.com";
	}

	@Override
	public String platformName() {
		return "河狸家";
	}

	@Override
	public String[] tags() {
		return new String[] {"购物" , "低价" , "美容", "女性"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13654022846", "18210538513");
	}

	@Override
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
