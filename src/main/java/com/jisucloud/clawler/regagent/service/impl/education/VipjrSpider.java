package com.jisucloud.clawler.regagent.service.impl.education;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "vipjr.com", 
		message = "vipJr青少儿在线教育致力于打造K12全学科在线教育平台,为5-18岁青少儿提供在线少儿英语学习、一对一英语口语、数学、编程、语文等多元化在线教学服务。", 
		platform = "vipjr", 
		platformName = "vipjr", 
		tags = { "家长", "在线教育" }, 
		testTelephones = { "15161509916", "18210538513" })
public class VipjrSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.vipjr.com/api/loginpost?brandId=4";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.vipjr.com")
					.addHeader("Referer", "https://passport.vipjr.com/member/tutorabc.html#/login")
					.post(FormBody.create(MediaType.parse("application/json;charset=utf-8"), "{\"brandId\":4,\"account\":\""+account+"\",\"password\":\"vcghvkjh98y897\",\"sourceType\":\"vipjr-vipjr\"}"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("E400403")) {
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
