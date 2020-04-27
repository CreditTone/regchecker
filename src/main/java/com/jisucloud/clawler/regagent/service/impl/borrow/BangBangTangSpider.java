package com.jisucloud.clawler.regagent.service.impl.borrow;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "rmbbox.com", 
		message = "邦帮堂由北京紫貔财富网络科技有限公司运营，坚持小额普惠原则，为用户提供网络借贷信息中介服务。", 
		platform = "rmbbox", 
		platformName = "邦帮堂", 
		tags = { "p2p", "借贷", "贷超" }, 
		testTelephones = { "18212345678", "15161509916" },
		ignoreTestResult = true)
public class BangBangTangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.rmbbox.com/register/checkMobile?enterprise=true&userRole=BORROWERS&mobile=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.rmbbox.com/register")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被")) {
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
