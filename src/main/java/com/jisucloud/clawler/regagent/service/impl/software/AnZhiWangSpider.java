package com.jisucloud.clawler.regagent.service.impl.software;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "anzhi.com", 
		message = "安卓市场,Android,安卓,安智市场-国内最专业的Android安卓手机电子市场，提供海量安卓软件、Android手机游戏、安卓最新汉化软件资源及最新APK汉化、汉化破解APP、APK免费下载，致力于为用户打造最贴心的Android安卓应用商店。", 
		platform = "anzhi", 
		platformName = "安智市场", 
		tags = { "系统工具" , "软件下载" }, 
		testTelephones = { "15901537458", "18212345678" })
public class AnZhiWangSpider extends PapaSpider {

	


	public boolean checkTelephone(String account) {
		try {
			String url = "http://i.anzhi.com/web/account/check-telephone?telephone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "i.anzhi.com")
					.addHeader("Referer", "http://i.anzhi.com/web/account/register?serviceId=005&serviceVersion=1.0&serviceType=1&redirecturi=http%3A%2F%2Fwww.anzhi.com%2F")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("false")) {
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
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
