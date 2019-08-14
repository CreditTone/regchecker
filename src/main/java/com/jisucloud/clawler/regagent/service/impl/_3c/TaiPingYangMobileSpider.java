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
	home = "pconline.com", 
	message = "太平洋电脑网是专业IT门户网站,为用户和经销商提供IT资讯和行情报价,涉及电脑,手机,数码产品,软件等。", 
	platform = "pconline", 
	platformName = "太平洋电脑网", 
	tags = { "3c", "科技", "智能手机" }, 
	testTelephones = { "15510257873", "18210538513" })
public class TaiPingYangMobileSpider extends PapaSpider {

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport3.pconline.com.cn/passport3/api/validate_mobile.jsp?mobile=" + account
					+ "&req_enc=UTF-8";
			String postData = "{}:";
			Request request = new Request.Builder().url(url).addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "passport3.pconline.com.cn")
					.addHeader("Referer", "https://my.pconline.com.cn/passport/mobileRegister.jsp")
					.post(FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), postData)).build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已经注册")) {
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
