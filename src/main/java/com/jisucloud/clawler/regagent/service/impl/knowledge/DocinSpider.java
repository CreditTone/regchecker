package com.jisucloud.clawler.regagent.service.impl.knowledge;


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
		home = "docin.com", 
		message = "豆丁网创立于2007年，是全球最大的中文社会化阅读平台，为用户提供一切有价值的可阅读之物。截至2010年，豆丁网已经成功跻身互联网全球500强，成为提供垂直服务的优秀网站之一。", 
		platform = "docin", 
		platformName = "豆丁网", 
		tags = { 
			"新闻阅读","小说","听书","教育"
		 }, 
		testTelephones = { "15070860150", "18212345678" })
public class DocinSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.docin.com/app/findPassword";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("login_email", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.docin.com")
					.addHeader("Referer", "https://www.docin.com/app/findPassword")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			Document doc = Jsoup.parse(response.body().string());
			if (doc.select("div.tips").text().contains("请输入验证码")) {
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
