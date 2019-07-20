package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class TongTongLiCaiSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "通通理财是一款短期金融投资理财APP，成立于2015年10月，公司注册资金1亿元，隶属于浙江通通金融信息服务有限公司， 为用户提供互联网金融投资理财服务，实现财富增值。 ";
	}

	@Override
	public String platform() {
		return "tongtongli";
	}

	@Override
	public String home() {
		return "tongtongli.com";
	}

	@Override
	public String platformName() {
		return "通通理财";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "理财"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
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
