package com.jisucloud.clawler.regagent.service.impl.game;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

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
@Component
public class SanQiGameSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "37游戏平台是专业的游戏运营平台,为中外游戏用户提供精品游戏;37游戏致力于游戏精细化运营与优质的客户服务,成为深受玩家喜爱的国际化品牌游戏运营商。";
	}

	@Override
	public String platform() {
		return "37";
	}

	@Override
	public String home() {
		return "37.com";
	}

	@Override
	public String platformName() {
		return "37游戏";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new SanQiGameSpider().checkTelephone("13910252045"));
//		System.out.println(new SanQiGameSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://my.37.com/api/register.php?action=checkUser&callback=jQuery1830635194860639588_"+System.currentTimeMillis()+"&login_account="+account+"&_="+System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "my.37.com")
					.addHeader("Referer", "https://www.37.com/")
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
