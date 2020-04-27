package com.jisucloud.clawler.regagent.service.impl.util;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "qichacha.com", 
		message = "企查查为您提供企业信息查询,工商查询,信用查询,公司查询等相关信息查询；帮您快速了解企业信息,企业工商信息,企业信用信息,企业失信信息等企业经营和人员投资状况,查询更多企业信息就到企查查官网。", 
		platform = "qichacha", 
		platformName = "企查查", 
		tags = { "工具" , "资讯" }, 
		userActiveness = 0.7f,
		testTelephones = { "18212345678", "15011008001" })
public class QiChaChaSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.qichacha.com/user_phonecheck";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("title", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qichacha.com")
					.addHeader("Referer", "https://www.qichacha.com/user_register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("false")) {
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
