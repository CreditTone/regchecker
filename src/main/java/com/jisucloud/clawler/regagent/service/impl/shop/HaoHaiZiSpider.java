package com.jisucloud.clawler.regagent.service.impl.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class HaoHaiZiSpider extends PapaSpider {

	@Override
	public String message() {
		return "儿童用品网上专卖店，婴幼儿用品网店，好孩子国际品牌是儿童用品行业唯一被授予'中国名牌'和'中国驰名商标'称号的品牌，保证产品质量，让您的网上购物轻松便捷，贴心安心。好孩子官方商城-你身边专。";
	}

	@Override
	public String platform() {
		return "haohaizi";
	}

	@Override
	public String home() {
		return "haohaizi.com";
	}

	@Override
	public String platformName() {
		return "好孩子商城";
	}

	@Override
	public String[] tags() {
		return new String[] {"电商" , "电器"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13419594450", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.haohaizi.com/passport-check_phone.html";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("module", "register")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://www.haohaizi.com/passport-signup.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return result.getString("resultValue").equals("3");
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
