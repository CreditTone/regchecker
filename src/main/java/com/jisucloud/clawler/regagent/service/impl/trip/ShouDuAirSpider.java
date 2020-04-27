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
		home = "jdair.com", 
		message = "北京首都航空官方网站,北京首都航空有限公司简称首都航空,是一家总部位于北京的公共运输航空公司。首都航空秉承“安全、简单、亲和”的服务理念。", 
		platform = "jdair", 
		platformName = "首都航空", 
		tags = { "出行" , "飞机" , "机票" }, 
		testTelephones = { "13912345678", "18212345678" })
public class ShouDuAirSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://cas.jdair.net/uniqueValidate";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobileNo", account)
	                .add("value", account)
	                .add("custNo", "")
	                .add("type", "mobile")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "cas.jdair.net")
					.addHeader("Referer", "https://cas.jdair.net/regist?execution=e1s1&_eventId=agree")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("请直接登录")) {
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
