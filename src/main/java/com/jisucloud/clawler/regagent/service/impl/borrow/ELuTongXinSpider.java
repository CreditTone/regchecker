package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "88bank.com", 
		message = "e路同心是由深圳市同心科创金融服务有限公司运营，于2015年4月18日正式上线，是由深圳市同心投资基金股份公司及广东省粤科金融集团联袂打造的国资系P2P平台，致力于为投资者提供优质的投资理财信息服务。", 
		platform = "88bank", 
		platformName = "88bankName", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class ELuTongXinSpider extends PapaSpider {
	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.88bank.com/secure/register/check-account";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.88bank.com")
					.addHeader("Referer", "https://www.88bank.com/secure/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
