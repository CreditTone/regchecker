package com.jisucloud.clawler.regagent.service.impl.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "jumei.com", 
		message = "聚美优品是一家化妆品限时特卖商城,其前身为团美网,由陈欧、戴雨森等创立于2010年3月。聚美优品首创“化妆品团购”模式:每天在网站推荐十几款热门化妆品。", 
		platform = "jumei", 
		platformName = "聚美优品", 
		tags = { "护肤品" , "化妆品" }, 
		testTelephones = { "18779861101", "18210538513" })
public class JuMeiYouPinSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.jumei.com/i/account/check_mobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Mobile Safari/537.36")
					.addHeader("Origin", "https://passport.jumei.com")
					.addHeader("Host", "passport.jumei.com")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("Referer", "https://passport.jumei.com/i/account/signup?redirect=http%3A%2F%2Fpihao.jumei.com%2F")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getIntValue("status") == 0) {
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
