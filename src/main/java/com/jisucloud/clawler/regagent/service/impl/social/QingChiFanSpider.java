package com.jisucloud.clawler.regagent.service.impl.social;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "qingchifan.com", 
		message = "请吃饭是一个通过美食进行快速线下交友的移动应用，使用请吃饭，可以花费信用发布约会，也可以报名别人发起的约会，请吃饭将线上交友转移到线下，安全快速交友。", 
		platform = "qingchifan", 
		platformName = "请吃饭", 
		tags = { "单身交友" , "约" }, 
		testTelephones = { "18210538000", "18210538513" })
public class QingChiFanSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://m.qingchifan.com/passport/webapp/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("password", "c229482cb")
	                .add("client_type", "1")
	                .add("apiVersion", "2.5.0")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "m.qingchifan.com")
					.addHeader("Referer", "http://m.qingchifan.com/login.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码错误")) {
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
