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
		home = "gomefinance.com", 
		message = "国美金融-国美控股旗下金融发展与投资业务战略管控平台,致力推动普惠金融的发展!国美金融,国美,家美,生活美!", 
		platform = "gomefinance", 
		platformName = "国美金融", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class GuoMeiJinRongSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.gomefinance.com.cn/api/v1/checkMobile?t=" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.gomefinance.com.cn")
					.addHeader("channel", "GMJRBDSSPPC")
					.addHeader("plant", "PC")
					.addHeader("Referer", "https://www.gomefinance.com.cn/src/html/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("该手机号已注册")) {
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
