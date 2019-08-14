package com.jisucloud.clawler.regagent.service.impl.social;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "paidai.com", 
		message = "派代网定位为中国电子商务的入口,目前是中国最活跃、最具影响力的电子商务行业交流平台,聚集了大量的电子商务领军企业创始人群。提供电商学习、人才招聘、企业贷款等!", 
		platform = "paidai", 
		platformName = "派代网", 
		tags = { "社交" , "人脉", "电商" , "电商运营" ,"网店" }, 
		testTelephones = { "18810038000", "18210538513" })
public class PaiDaiWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.paidai.com/user/register.php?act=ajax_chkiphone&iphone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.paidai.com/user/register.php")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			JSONObject result = JSON.parseObject(res);
			return result.getString("r").equals("2");
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
