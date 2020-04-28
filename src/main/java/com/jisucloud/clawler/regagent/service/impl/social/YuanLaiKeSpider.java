package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep007.goniub.okhttp.OKHttpUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;

@Slf4j
@PapaSpiderConfig(
		home = "ylike.com", 
		message = "缘来客交友网是拥有多年口碑的大型同城约会交友网站,缘来客手机版专为同城男女提供同城约会手机登录,缘来客交友网拥有1200万同城交友会员,使用缘来客手机版登录。", 
		platform = "ylike", 
		platformName = "缘来客", 
		tags = { "单身交友", "社区" }, 
		testTelephones = { "18515290717", "18212345678" })
public class YuanLaiKeSpider extends PapaSpider {
	
	private String getRegCck() throws Exception {
		String url = "http://www.ylike.com/Reg_Base.do?action=OK";
		Request request = new Request.Builder().url(url)
				.addHeader("User-Agent", CHROME_USER_AGENT)
				.addHeader("Host", "www.ylike.com")
				.build();
		Response response = okHttpClient.newCall(request).execute();
		String res = response.body().string();
		return Jsoup.parse(res).getElementById("RegCck").attr("value");
	}


	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.ylike.com/g/getCheck_Data.do?dt=" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("val", account)
	                .add("cln", "UserName")
	                .add("RegCck", getRegCck())
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "www.ylike.com")
					.addHeader("Referer", "http://www.ylike.com/Reg_Base.do?action=OK")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
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
