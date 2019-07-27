package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

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
public class LaQuWangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "免费试用平台拉趣网:拉趣网一个全国领先的免费试用网和试客网,深得试客信赖的免费试用中心网站,是免费试用网和试客网站的首选,拉趣免费试用网为试客提供最.";
	}

	@Override
	public String platform() {
		return "laqu";
	}

	@Override
	public String home() {
		return "laqu.com";
	}

	@Override
	public String platformName() {
		return "拉趣网";
	}

	@Override
	public String[] tags() {
		return new String[] {"9.9包邮" , "试用" , "便宜货"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13800000000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.laqu.com/user/person/forgot/auth";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("nick", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://www.laqu.com/user/person/forgot")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("成功")) {
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
