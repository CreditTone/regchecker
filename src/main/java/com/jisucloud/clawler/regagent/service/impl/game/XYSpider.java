package com.jisucloud.clawler.regagent.service.impl.game;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class XYSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "网页游戏第一平台::XY.COM::网页游戏,网页游戏排行榜,最新网页游戏开服表,最全的网页游戏资讯和网页游戏开服礼包,玩精品网页游戏尽在xy.com。";
	}

	@Override
	public String platform() {
		return "xy";
	}

	@Override
	public String home() {
		return "xy.com";
	}

	@Override
	public String platformName() {
		return "xy游戏";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "13269423806");
	}

	@Override
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
