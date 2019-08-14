package com.jisucloud.clawler.regagent.service.impl.game;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "xy.com", 
		message = "网页游戏第一平台::XY.COM::网页游戏,网页游戏排行榜,最新网页游戏开服表,最全的网页游戏资讯和网页游戏开服礼包,玩精品网页游戏尽在xy.com。", 
		platform = "xy", 
		platformName = "XY游戏", 
		tags = { "游戏" }, 
		testTelephones = { "18210538513", "13269423806" })
public class XYSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.xy.com/account/check/mobile?v="+(System.currentTimeMillis() / 1000)+"&mobile=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.xy.com")
					.addHeader("Referer", "https://www.xy.com/account/register?type=mobile")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string().toLowerCase();
			if (res.contains("false")) {
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
