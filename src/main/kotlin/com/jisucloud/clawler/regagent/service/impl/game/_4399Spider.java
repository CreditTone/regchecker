package com.jisucloud.clawler.regagent.service.impl.game;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class _4399Spider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "4399是领先的小游戏专业网站,免费为你提供小游戏大全,4399洛克王国小游戏,双人小游戏,连连看小游戏,赛尔号,奥拉星,奥奇传说小游戏,造梦西游3等最新小游戏。";
	}

	@Override
	public String platform() {
		return "4399";
	}

	@Override
	public String home() {
		return "4399.com";
	}

	@Override
	public String platformName() {
		return "4399游戏";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new _4399Spider().checkTelephone("18720982607"));
//		System.out.println(new _4399Spider().checkTelephone("18210538513"));
//	}

	@Override
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
