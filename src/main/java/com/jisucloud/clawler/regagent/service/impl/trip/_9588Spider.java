package com.jisucloud.clawler.regagent.service.impl.trip;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "9588.com", 
		message = "9588旅行网,提供国际国内机票、北京机票、飞机票、特价机票、电子机票、电子客票、酒店等查询预订的在线旅行服务,订票、订机票价格优惠,自由行、支持手机WAP订票等,全国五十多个城市可配送。", 
		platform = "9588", 
		platformName = "9588旅行网", 
		tags = { "旅行", "机票", "酒店" }, 
		testTelephones = { "15120058878", "18212345678" })
public class _9588Spider extends PapaSpider {


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://www.9588.com/Home/ExistCustomer?&r=582.0728615140399&clientid=txtMobile&txtMobile="+account+"&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.9588.com/Home/Register")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().toUpperCase().contains("YES");
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
