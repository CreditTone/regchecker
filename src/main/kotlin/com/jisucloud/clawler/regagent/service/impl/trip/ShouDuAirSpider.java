package com.jisucloud.clawler.regagent.service.impl.trip;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ShouDuAirSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "北京首都航空官方网站,北京首都航空有限公司简称首都航空,是一家总部位于北京的公共运输航空公司。首都航空秉承“安全、简单、亲和”的服务理念。";
	}

	@Override
	public String platform() {
		return "jdair";
	}

	@Override
	public String home() {
		return "jdair.com";
	}

	@Override
	public String platformName() {
		return "首都航空";
	}

	@Override
	public String[] tags() {
		return new String[] {"出行" , "飞机" , "机票"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
	}

	@Override
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
