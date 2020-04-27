package com.jisucloud.clawler.regagent.service.impl.social;


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
		home = "youyuan.com", 
		message = "有缘网是中国领先的大众婚恋交友移动互联网平台,专注于为最广泛的年轻单身男女婚恋交友创造更多机会和可能。找对象,上有缘网!", 
		platform = "youyuan", 
		platformName = "有缘网", 
		tags = { "单身交友" , "婚恋" }, 
		testTelephones = { "18810030000", "18212345678" })
public class YouYuanWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.youyuan.com/json/username.html";
			String postdata = "username="+account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("Referer", "http://www.youyuan.com/")
					.post(FormBody.create(MediaType.get("application/x-www-form-urlencoded"), postdata))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String errorMsg = response.body().string();
			if (errorMsg.contains("true")) {
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
