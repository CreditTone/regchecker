package com.jisucloud.clawler.regagent.service.impl.photo;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "huaban.com", 
		message = "花瓣网, 设计师寻找灵感的天堂！图片素材领导者，帮你采集,发现网络上你喜欢的事物.你可以用它收集灵感,保存有用的素材,计划旅行,晒晒自己想要的东西。", 
		platform = "huaban", 
		platformName = "花瓣网", 
		tags = { "原创" , "设计", "素材" }, 
		testTelephones = { "13991808887", "15120058878" })
public class HuaBanWangSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://huaban.com/auth/";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://huaban.com/")
					.post(createUrlEncodedForm("email="+account+"&password=dasdas231123&_ref=frame"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("用户密码错误")) {
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
