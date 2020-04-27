package com.jisucloud.clawler.regagent.service.impl._3c;

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
		home = "zealer.com", 
		message = "自如评测是一个科技视频平台，于2012年11月8日正式上线，创始人兼 CEO 王自如和 ZEALER 坚持打造兼具行业洞察力与品质感的科技视频内容，输出「科技 Plus」生活主张。", 
		platform = "zealer", 
		platformName = "自如评测", 
		tags = { "3c", "科技" ,"智能手机", "手机评测" }, 
		testTelephones = { "15510257873", "18212345678" })
public class ZealerTestSpider extends PapaSpider {

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://api.account.zealer.com/account/login/password";
			String postData = "{\"account\":\""+account+"\",\"password\":\"dasdas1213123\"}";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "api.account.zealer.com")
					.addHeader("Referer", "https://www.zealer.com/#/account/login")
					.post(FormBody.create(MediaType.parse("application/json;charset=UTF-8"), postData))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("41106")) {
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
