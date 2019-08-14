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
		home = "wanmei.com", 
		message = "完美世界控股集团... 完美世界控股集团 完美世界影视 完美世界游戏 完美世界影城 完美世界动画 完美世界文学 完美世界媒体 完美世界教育新闻报道 新闻动态 完美世界商城。", 
		platform = "wanmei", 
		platformName = "完美世界", 
		tags = { "游戏" }, 
		testTelephones = { "13426345414", "13269423806" })
public class WanMeiShiJieSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.wanmei.com/reg/checkuser?username=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.wanmei.com")
					.addHeader("Referer", "https://passport.wanmei.com/reg/?s=pay")
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
