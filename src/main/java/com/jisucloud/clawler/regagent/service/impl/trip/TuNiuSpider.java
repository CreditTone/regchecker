package com.jisucloud.clawler.regagent.service.impl.trip;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "tuniu.com", 
		message = "途牛旅游网提供跟团游、自助游、邮轮旅游、自驾游、定制游以及景点门票预订、机票预订、火车票预订服务,还有牛人专线、首付出发旅游等品质高端。", 
		platform = "tuniu", 
		platformName = "途牛旅游网", 
		tags = { "旅游" , "酒店" , "美食" , "o2o" }, 
		testTelephones = { "13910252075", "18212345678" })
public class TuNiuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.tuniu.com/register/isPhoneAvailable";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("intlCode", "0086")
	                .add("tel", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.tuniu.com")
					.addHeader("Referer", "https://passport.tuniu.com/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已经注册") || res.contains("\\u7ecf\\u6ce8\\u518c\\u3002")) {
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
