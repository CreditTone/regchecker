package com.jisucloud.clawler.regagent.service.impl.news;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "people.com", 
		message = "人民网,是世界十大报纸之一《人民日报》建设的以新闻为主的大型网上信息发布平台,也是互联网上最大的中文和多语种新闻网站之一。作为国家重点新闻网站。", 
		platform = "people", 
		platformName = "人民网", 
		tags = { "新闻", "资讯" }, 
		testTelephones = { "18720982007", "18212345678" })
public class RenMinWang extends PapaSpider {

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://sso.people.com.cn/u/reg/checkPhoneNum?phoneNum="+account+"&_="+System.currentTimeMillis()+"&type=";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "sso.people.com.cn")
					.addHeader("Referer", "http://sso.people.com.cn/u/reg?appCode=&fromUrl=")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("false")) {
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
