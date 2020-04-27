package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;
import java.util.Random;



@Slf4j
@PapaSpiderConfig(
		home = "hzed.com", 
		message = "合众e贷专注于车贷与消费金融的互联网金融P2P网贷平台,具有回报价值与投资者信赖的互联网金融服务机构,为广大投资者提供安全、高效、透明的互联网投资理财平台。", 
		platform = "hzed", 
		platformName = "合众e贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268901", "18212345678" })
public class HeZhogEDaiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.hzed.com/front/account/hasMobileExists?mobile=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.hzed.com")
					.addHeader("Referer", "https://www.hzed.com/register")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("号码被使用")) {
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
