package com.jisucloud.clawler.regagent.service.impl.music;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@UsePapaSpider
public class KugouSpider implements PapaSpider {
	
	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "酷狗音乐旗下最新最全的在线正版音乐网站,本站为您免费提供最全的在线音乐试听下载,以及全球海量电台和MV播放服务、最新音乐播放器下载。酷狗音乐 和音乐在一起。";
	}

	@Override
	public String platform() {
		return "kugou";
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985260000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://m.kugou.com/forgetpwd/Checkuser?username=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A402 Safari/604.1")
					.addHeader("Host", "m.kugou.com")
					.addHeader("Referer", "http://m.kugou.com/forget.php?act=html&view=0")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("mobile").length() > 3) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return checkTelephone(account);
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

	@Override
	public String platformName() {
		return "酷狗";
	}

	@Override
	public String home() {
		return "kugou.com";
	}

	@Override
	public String[] tags() {
		return new String[] { "音乐" };
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new KugouSpider().checkTelephone("13910250000"));
//		System.out.println(new KugouSpider().checkTelephone("18210538513"));
//	}

}
