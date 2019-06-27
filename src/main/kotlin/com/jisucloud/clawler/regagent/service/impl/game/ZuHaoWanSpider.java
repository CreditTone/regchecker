package com.jisucloud.clawler.regagent.service.impl.game;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZuHaoWanSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "租号玩-国内游戏租号服务平台,海量账号出租,吃鸡.CF.LOL.飞车等热门游戏均可租用体验,神级装备，大神账号等你来租, 上号器极速上号.租赁网游账号，体验游戏乐趣，享受虐菜快感！买号太贵，练号太累,来租号玩！";
	}

	@Override
	public String platform() {
		return "zuhaowan";
	}

	@Override
	public String home() {
		return "zuhaowan.com";
	}

	@Override
	public String platformName() {
		return "租号玩";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏" , "游戏租号"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZuHaoWanSpider().checkTelephone("13877117175"));
//		System.out.println(new ZuHaoWanSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.zuhaowan.com/Login/checkUserName.html";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.zuhaowan.com")
					.addHeader("Referer", "https://www.zuhaowan.com/login/register.html")
					.post(formBody)
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
