package com.jisucloud.clawler.regagent.service.impl.law;


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
		home = "yifatong.com", 
		message = "易法通专注在线法律咨询和法律咨询服务,提供多方位的合同起草审查、打官司、法律顾问服务。找法律在线解答、法律咨询服务就上易法通。", 
		platform = "yifatong", 
		platformName = "易法通", 
		tags = { "法律咨询" , "找律师" }, 
		testTelephones = { "18212345678", "13761990875" })
public class YiFaTongSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.yifatong.com/Customers/validateField";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.yifatong.com")
					.addHeader("Referer", "http://www.yifatong.com/Customers/registration?url=")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("已被使用")) {
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
