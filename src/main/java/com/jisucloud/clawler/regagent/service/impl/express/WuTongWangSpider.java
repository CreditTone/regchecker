package com.jisucloud.clawler.regagent.service.impl.express;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "chinawutong.com", 
		message = "物通网是集物流查询、物流配货的专业一站式物流货运信息网,是货运物流公司、货车、快递公司、搬家公司、海运公司、空运公司、发货商的汇聚地,是物流货运信息非常全面。", 
		platform = "chinawutong", 
		platformName = "物通网", 
		tags = { "物流" ,"生意" }, 
		testTelephones = { "18210538513", "13761990875" })
public class WuTongWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.chinawutong.com/ashx/RegHandler1.ashx?Action=PhoneTest&Phone=" + account;
			FormBody formBody = new FormBody
	                .Builder()
	                .add("Action", "PhoneTest")
	                .add("Phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.chinawutong.com")
					.addHeader("Referer", "http://www.chinawutong.com/regdo.aspx?type=3")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("0")) {
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
