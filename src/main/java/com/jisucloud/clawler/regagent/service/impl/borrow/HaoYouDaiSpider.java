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
		home = "hydbest.com", 
		message = "好又贷新网银行资金存管,注册实缴资金1亿元,好又贷是一家财富管理的互联网金融投资平台,为借贷双方提供便捷的中介信息服务。", 
		platform = "hydbest", 
		platformName = "好又贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class HaoYouDaiSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.hydbest.com/Account/CheckMobilePhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobilePhone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.hydbest.com")
					.addHeader("Referer", "http://www.hydbest.com/Account/Register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被使用")) {
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
