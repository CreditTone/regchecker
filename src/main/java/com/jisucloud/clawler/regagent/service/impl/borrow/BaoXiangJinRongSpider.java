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
		home = "bxjr.com", 
		message = "宝象金融是集借款、理财于一体的综合性金融服务平台。是一家专注于农业供应链金融领域的互联网金融平台。宝象金融于2015年4月正式上线。", 
		platform = "bxjr", 
		platformName = "宝象金融", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" },
		exclude = true, excludeMsg = "倒闭")
public class BaoXiangJinRongSpider extends PapaSpider {
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.bxjr.com/validate/ajax-mobile.html";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("param", account)
	                .add("name", "mobile")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.bxjr.com")
					.addHeader("Referer", "https://www.bxjr.com/secure/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被使用")) {
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
