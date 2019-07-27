package com.jisucloud.clawler.regagent.service.impl.game;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class BingChuanSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "冰川网络发展至今旗下已经拥有了大型网络游戏:《影武者》《远征OL》《龙武》《不败传说》,影武者 8月11号首次不限号内测、现已开启预约。";
	}

	@Override
	public String platform() {
		return "bingchuan";
	}

	@Override
	public String home() {
		return "q1.com";
	}

	@Override
	public String platformName() {
		return "冰川网络";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://passport.q1.com/Validate/UserName?q=9500.26103947831";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userName", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0") 
					.addHeader("Host", "passport.q1.com")
					.addHeader("Referer", "http://passport.q1.com/mobileregister.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("0")) {
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
