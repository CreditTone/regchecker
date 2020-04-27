package com.jisucloud.clawler.regagent.service.impl.trip;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "podinns.com", 
		message = "布丁酒店连锁是全国快捷连锁酒店前10强,为客户提供经济型客房,支持在线酒店预订,电话预订,wap预订,手机客户端预订,手机版预订。", 
		platform = "podinns", 
		platformName = "布丁酒店", 
		tags = { "旅行", "酒店" }, 
		testTelephones = { "13810973590", "18212345678" })
public class BuDingJiuDianSpider extends PapaSpider {


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://www.podinns.com/Account/MobileExist?value=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.9588.com/Home/Register")
					.post(createUrlEncodedForm(""))
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().toUpperCase().contains("FALSE");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
