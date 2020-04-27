package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "dib66.com", 
		message = "地标金融国内首批由第三方做资金托管账户的P2P网贷平台,网贷投资专业首选—地标金融。地标金融网络借贷平台,线上P2P投资方便快捷!", 
		platform = "dib66", 
		platformName = "地标金融", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class DiBiaoJinRongSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.dib66.com/ajaxCheckLog.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("param", account)
	                .add("name", "paramMap.email")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.dib66.com")
					.addHeader("Referer", "https://www.dib66.com/secure/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("\"y\"")) {
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
