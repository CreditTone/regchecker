package com.jisucloud.clawler.regagent.service.impl.life;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "2345.com", 
		message = "2345.com热门网址导航站网罗精彩实用网址，如音乐、小说、NBA、财经、购物、视频、软件及热门游戏网址大全等，提供了多种搜索引擎入口、实用查询、天气预报、个性定制等实用功能，帮助广大网友畅.", 
		platform = "2345dh", 
		platformName = "2345导航", 
		tags = { "门户网址", "资讯" }, 
		testTelephones = { "13771025665", "18210538513" })
public class _2345Spider extends PapaSpider {


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "https://passport.2345.com/webapi/check/jsonp?callback=jQuery183019306&value="+account+"&with=0&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://passport.2345.com/find?type=password")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().contains("(1)");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
