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
		home = "dobest.cn", 
		message = "游卡桌游是中国大陆第一家集设计、开发、推广和运营于一体的专业桌面游戏公司,以“创造和分享快乐”为使命,整合行业资源,培养行业人才,以实际行动推动桌游产业发展。", 
		platform = "dobest", 
		platformName = "游卡桌游", 
		tags = { "游戏" }, 
		testTelephones = { "18779861101", "18210538511" })
public class YouKaZhuoYouSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://splus2.dobest.cn/api/reg/check/mobile?mobile=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "splus2.dobest.cn")
					.addHeader("Referer", "https://splus2.dobest.cn/register")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
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
