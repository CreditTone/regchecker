package com.jisucloud.clawler.regagent.service.impl.reader;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "kongfz.com", 
		message = "孔夫子旧书网是国内领先的古旧书交易平台,汇集全国各地13000家网上书店,50000家书摊,展示多达9000万种书籍;大量极具收藏价值的古旧珍本。", 
		platform = "kongfz", 
		platformName = "孔夫子旧书网", 
		tags = { "书城" }, 
		testTelephones = { "18660396405", "18212345678" })
public class KongFuziSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://login.kongfz.com/Pc/Verify/mobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("smsBizType", "2")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "login.kongfz.com")
					.addHeader("Referer", "http://www.kongfz.com/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已存在")) {
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
