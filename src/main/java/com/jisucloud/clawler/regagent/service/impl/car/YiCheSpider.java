package com.jisucloud.clawler.regagent.service.impl.car;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class YiCheSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "易车网为您提供车市行情、汽车市场行情、汽车价格、汽车报价信息。车市最新报价，优惠信息，新车及经销商信息，汽车团购服务等，是您选车购车的第一网络平台。";
	}

	@Override
	public String platform() {
		return "yiche";
	}

	@Override
	public String home() {
		return "yiche.com";
	}

	@Override
	public String platformName() {
		return "易车网";
	}

	@Override
	public String[] tags() {
		return new String[] {"二手车", "卖车", "买车"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18611216720", "18210538513");
	}

	@Override
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
