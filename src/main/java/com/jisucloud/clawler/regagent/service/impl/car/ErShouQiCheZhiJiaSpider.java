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
		home = "che168.com", 
		message = "二手车之家二手车,提供二手车报价,众多二手车商家入驻,二手车源信息真实完善,选车放心、便捷,是最大的二手车交易市场,买卖二手车就上二手车之家。", 
		platform = "che168", 
		platformName = "二手车之家", 
		tags = { "二手车", "卖车", "买车" }, 
		testTelephones = { "18210538577", "18212345678" })
public class ErShouQiCheZhiJiaSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.che168.com/AccountApi/CheckPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone",account)
	                .add("isOverSea", "0")
	                .add("validcodetype", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.che168.com")
					.addHeader("Referer", "https://account.che168.com/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("0")) {
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
