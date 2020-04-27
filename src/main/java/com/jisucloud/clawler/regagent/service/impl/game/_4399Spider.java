package com.jisucloud.clawler.regagent.service.impl.game;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "4399.com", 
		message = "4399是领先的小游戏专业网站,免费为你提供小游戏大全,4399洛克王国小游戏,双人小游戏,连连看小游戏,赛尔号,奥拉星,奥奇传说小游戏,造梦西游3等最新小游戏。", 
		platform = "4399", 
		platformName = "4399游戏网", 
		tags = { "游戏" }, 
		testTelephones = { "18720982607", "18212345678" })
public class _4399Spider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://ptlogin.4399.com/ptlogin/isExist.do?username="+account+"&appId=oauth&regMode=reg_phone&v=1";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "ptlogin.4399.com")
					.addHeader("Referer", "https://ptlogin.4399.com/oauth2/authorize.do")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已被注册")) {
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
