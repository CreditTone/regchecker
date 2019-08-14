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
		home = "cgtz.com", 
		message = "草根投资是国内领先的P2P理财平台,为投资者提供多元化的理财产品,在安全的基础上保障高收益,是投资理财首选平台。", 
		platform = "cgtz", 
		platformName = "cgtzName", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13910252045", "18210538513" })
public class CaoGenTouZiSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.cgtz.com/find_pwd/check_mobile.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.cgtz.com")
					.addHeader("Referer", "https://www.cgtz.com/user/info/find_password.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("success\":false")) {
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
