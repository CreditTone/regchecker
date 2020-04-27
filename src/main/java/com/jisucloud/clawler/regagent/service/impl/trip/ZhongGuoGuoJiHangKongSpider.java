package com.jisucloud.clawler.regagent.service.impl.trip;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "airchina.com", 
		message = "中国国际航空股份有限公司官网，提供国际国内飞机票查询、航班查询、特价打折机票预订服务。", 
		platform = "airchina", 
		platformName = "中国国际航空", 
		tags = { "出行" , "飞机" , "机票" }, 
		testTelephones = { "19910002005", "18212345678" })
public class ZhongGuoGuoJiHangKongSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.airchina.com.cn/www/servlet/com.ace.um.userRegister.servlet.PhoneValidator";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("shouji",account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.airchina.com.cn")
					.addHeader("Referer", "http://www.airchina.com.cn/www/jsp/userManager/register.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
