package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "tongtongli.com", 
		message = "通通理财是一款短期金融投资理财APP，成立于2015年10月，公司注册资金1亿元，隶属于浙江通通金融信息服务有限公司， 为用户提供互联网金融投资理财服务，实现财富增值。 ", 
		platform = "tongtongli", 
		platformName = "通通理财", 
		tags = { "P2P", "理财" }, 
		testTelephones = { "15985268904", "18212345678" })
public class TongTongLiCaiSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.tongtongli.com/check_user_register.json";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", IOS_USER_AGENT)
					.addHeader("Host", "m.tongtongli.com")
					.addHeader("Referer", "https://www.bxjr.com/secure/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return result.getBooleanValue("data");
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
