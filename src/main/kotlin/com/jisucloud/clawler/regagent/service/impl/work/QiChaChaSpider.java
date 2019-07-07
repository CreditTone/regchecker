package com.jisucloud.clawler.regagent.service.impl.work;

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
public class QiChaChaSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "企查查为您提供企业信息查询,工商查询,信用查询,公司查询等相关信息查询；帮您快速了解企业信息,企业工商信息,企业信用信息,企业失信信息等企业经营和人员投资状况,查询更多企业信息就到企查查官网。";
	}

	@Override
	public String platform() {
		return "qichacha";
	}

	@Override
	public String home() {
		return "qichacha.com";
	}

	@Override
	public String platformName() {
		return "企查查";
	}

	@Override
	public String[] tags() {
		return new String[] {"工具" , "资讯"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15011008001");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.qichacha.com/user_phonecheck";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("title", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qichacha.com")
					.addHeader("Referer", "https://www.qichacha.com/user_register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("false")) {
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
