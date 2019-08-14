package com.jisucloud.clawler.regagent.service.impl.game;


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
		home = "kunlun.com", 
		message = "昆仑游戏是昆仑万维旗下游戏平台，主要针对手游、页游、端游的研发与发行。", 
		platform = "kunlun", 
		platformName = "昆仑游戏", 
		tags = { "游戏" }, 
		testTelephones = { "18779861102", "13269423806" })
public class KunLunSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.kunlun.com/index.php?act=ajax.checkUsername&v1=1.1&user_name=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.kunlun.com")
					.addHeader("Referer", "https://www.kunlun.com/?act=passport.regist&refurl=https%3A%2F%2Fwww.kunlun.com%2F%3Fact%3Dpassport.usercenter")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("3")) {
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
