package com.jisucloud.clawler.regagent.service.impl.car;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class PCautoSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "太平洋汽车网（PCauto）是一个专业的汽车网站。自2002年7月成立以来，以自身魅力迅速引起业界的瞩目，赢得了广大网友的拥护。以资讯、导购、导用、社区为出发点，坚持原创风格。";
	}

	@Override
	public String platform() {
		return "pcauto";
	}

	@Override
	public String home() {
		return "pcauto.com";
	}

	@Override
	public String platformName() {
		return "太平洋汽车网";
	}

	@Override
	public String[] tags() {
		return new String[] {"二手车", "汽车资讯"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210530000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport3.pcauto.com.cn/passport3/api/validate_mobile.jsp?mobile="+account+"&req_enc=UTF-8";
			RequestBody formBody = FormBody.create(MediaType.get("application/x-www-form-urlencoded"), "{}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport3.pcauto.com.cn")
					.addHeader("Origin", "https://my.pcauto.com.cn")
					.addHeader("Referer", "https://my.pcauto.com.cn/passport/mobileRegister.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已经注册过")) {
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
