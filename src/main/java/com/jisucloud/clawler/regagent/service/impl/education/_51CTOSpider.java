package com.jisucloud.clawler.regagent.service.impl.education;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "51cto.com", 
		message = "51CTO学院IT职业在线教育平台是依托12年行业品牌、1400万IT技术用户建立的专业IT技能学习培训平台,已签约1000多位技术专家发布了12万个自学式实战视频教程。", 
		platform = "51cto", 
		platformName = "51CTO学院", 
		tags = { "程序员","it","网络技术" }, 
		testTelephones = { "18720982607", "18210538513" })
public class _51CTOSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			Response response = okHttpClient.newCall(new Request.Builder().url("https://home.51cto.com/user/register?reback=http%3A%2F%2Fwww.51cto.com%2F&regtype=mobile").build()).execute();
			Document doc = Jsoup.parse(response.body().string());
			String csrf = doc.select("input[name=_csrf]").attr("value");
			String url = "https://home.51cto.com/user/check-mobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "home.51cto.com")
					.addHeader("Referer", "https://home.51cto.com/user/register?reback=http%3A%2F%2Fwww.51cto.com%2F&regtype=mobile")
					.addHeader("X-CSRF-Token", csrf)
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
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
