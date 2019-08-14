package com.jisucloud.clawler.regagent.service.impl.borrow;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "honhe.com", 
		message = "后河财富(www.honhe.com)是一家专注于汽车抵押债权投资的互联网金融投资理财平台,为用户提供安全,透明,灵活,高效,便捷的互联网理财服务。投资理财用户可通过后河。", 
		platform = "honhe", 
		platformName = "后河财富", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18210538513", "15161509916" })
public class HouHeCaiFuSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.honhe.com/new/valiPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.honhe.com/retakePwd/retake_pwd_page.jhtml")
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
