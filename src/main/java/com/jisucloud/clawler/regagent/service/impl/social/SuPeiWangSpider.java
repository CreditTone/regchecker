package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep007.spiderbase.okhttp.OKHttpUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@PapaSpiderConfig(
		home = "supei.com", 
		message = "速配网是面向全球华人的自助式征婚网站,免费注册,我们为真诚会员提供更多免费婚恋服务! - 速配网专注于网上征婚,不是婚介,不做交友,不良交友目的者请勿进入。", 
		platform = "supei", 
		platformName = "速配网", 
		tags = { "单身交友", "社区" }, 
		testTelephones = { "18515290717", "18212345678" })
public class SuPeiWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.supei.com/www/login.jsp";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("loginid", account)
	                .add("password", "Useas121rName")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "www.supei.com")
					.addHeader("Referer", "http://www.supei.com/www/loginpage.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			Document doc = Jsoup.parse(response.body().string());
			if (doc.select("font[color=red]").text().contains("密码")) {
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
