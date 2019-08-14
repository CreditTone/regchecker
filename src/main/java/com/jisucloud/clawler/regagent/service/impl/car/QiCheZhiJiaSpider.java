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
		home = "autohome.com", 
		message = "汽车之家为您提供最新汽车报价，汽车图片，汽车价格大全，最精彩的汽车新闻、行情、评测、导购内容，是提供信息最快最全的中国汽车网站。", 
		platform = "autohome", 
		platformName = "汽车之家", 
		tags = { "二手车", "汽车" }, 
		testTelephones = { "18210538500", "18210538513" })
public class QiCheZhiJiaSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.autohome.com.cn/AccountApi/CheckPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("isOverSea", "0")
	                .add("validcodetype", "1")
	                .add("", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.autohome.com.cn")
					.addHeader("Referer", "https://account.autohome.com.cn/register?backurl=https%253A%252F%252Fwww.autohome.com.cn%252Fbeijing%252F&fPosition=10001&sPosition=1000100&platform=1&pvareaid=3311227")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("MobileExist")) {
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
