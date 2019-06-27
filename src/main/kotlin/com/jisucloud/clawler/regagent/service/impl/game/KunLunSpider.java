package com.jisucloud.clawler.regagent.service.impl.game;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class KunLunSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "昆仑游戏是昆仑万维旗下游戏平台，主要针对手游、页游、端游的研发与发行。";
	}

	@Override
	public String platform() {
		return "kunlun";
	}

	@Override
	public String home() {
		return "kunlun.com";
	}

	@Override
	public String platformName() {
		return "昆仑游戏";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new KunLunSpider().checkTelephone("13269423806"));
//		System.out.println(new KunLunSpider().checkTelephone("18779861102"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.kunlun.com/index.php?act=ajax.checkUsername&v1=1.1&user_name=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.kunlun.com")
					.addHeader("Referer", "https://www.kunlun.com/?act=passport.regist&refurl=https%3A%2F%2Fwww.kunlun.com%2F%3Fact%3Dpassport.usercenter")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("3")) {
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
