package com.jisucloud.clawler.regagent.service.impl.car;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "yiche.com", 
		message = "易车网为您提供车市行情、汽车市场行情、汽车价格、汽车报价信息。车市最新报价，优惠信息，新车及经销商信息，汽车团购服务等，是您选车购车的第一网络平台。", 
		platform = "yiche", 
		platformName = "易车网", 
		tags = { "二手车", "卖车", "买车" }, 
		testTelephones = { "18611216720", "18212345678" })
public class YiCheSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://i.yiche.com/ajax/authenservice/MobileCode.ashx";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("mobileCodeKey", "register")
	                .add("smsType", "0")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "i.yiche.com")
					.addHeader("Referer", "http://i.yiche.com/authenservice/RegisterSimple/MobileRegister.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("key\":\"exist")) {
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
