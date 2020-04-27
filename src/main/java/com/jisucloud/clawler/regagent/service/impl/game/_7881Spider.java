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
		home = "7881.com", 
		message = "7881游戏交易平台-为您提供专业的游戏币交易、金币交易、账号交易、装备交易、道具交易、点卡点券交易、游戏租号,游戏代练、手游交易等买卖服务。", 
		platform = "7881", 
		platformName = "7881游戏交易平台", 
		tags = { "游戏" }, 
		testTelephones = { "18720982607", "18212345678" })
public class _7881Spider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.7881.com/user/examineRegisterUserTel.action";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("tel", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.7881.com")
					.addHeader("Referer", "http://www.7881.com/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已存在")) {
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
