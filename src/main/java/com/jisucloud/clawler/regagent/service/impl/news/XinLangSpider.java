package com.jisucloud.clawler.regagent.service.impl.news;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "sina.com", 
		message = "新浪网为全球用户24小时提供全面及时的中文资讯，内容覆盖国内外突发新闻事件、体坛赛事、娱乐时尚、产业资讯、实用信息等，设有新闻、体育、娱乐、财经、科技、房产、汽车等30多个内容频道，同时开设博客、视频、论坛等自由互动交流空间。", 
		platform = "sina", 
		platformName = "新浪网", 
		tags = { "新闻资讯" }, 
		testTelephones = { "18720982007", "18212345678" })
public class XinLangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://login.sina.com.cn/signup/check_user.php";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("name", account)
	                .add("format", "json")
	                .add("from", "mobile")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "login.sina.com.cn")
					.addHeader("Referer", "https://login.sina.com.cn/signup/signup?entry=homepage")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("100001")) {
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
