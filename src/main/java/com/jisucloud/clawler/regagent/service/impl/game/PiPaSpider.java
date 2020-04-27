package com.jisucloud.clawler.regagent.service.impl.game;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "pipaw.com", 
		message = "琵琶网是中国手机游戏门户网站,提供最新最好玩的手机网游下载,安卓游戏,苹果iPhone网游,新闻资讯,攻略评测,新游激活码礼包,手机网游排行,道具商城,及手游论坛交流等。", 
		platform = "pipaw", 
		platformName = "琵琶网", 
		tags = { "游戏" }, 
		testTelephones = { "18779861102", "18212345678" })
public class PiPaSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://passport.pipaw.com/user/checkusername";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.pipaw.com")
					.addHeader("Referer", "http://www.pipaw.com/")
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
