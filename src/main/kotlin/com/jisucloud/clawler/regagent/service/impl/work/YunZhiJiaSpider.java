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
public class YunZhiJiaSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "金蝶云之家是国内先进的移动办公平台,传承金蝶25余年管理经验,以组织/消息/社交为核心,提供OA系统、移动审批、考勤、会议等移动办公SaaS应用,助力企业高效智能办公!";
	}

	@Override
	public String platform() {
		return "yunzhijia";
	}

	@Override
	public String home() {
		return "yunzhijia.com";
	}

	@Override
	public String platformName() {
		return "金蝶云之家";
	}

	@Override
	public String[] tags() {
		return new String[] {"工具" , "财务软件" , "saas"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538511", "13953679455");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.yunzhijia.com/space/c/rest/user/pre-signup";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("email", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.yunzhijia.com")
					.addHeader("Referer", "https://www.yunzhijia.com/space/c/user-activate/registerUserByMobile?_t="+System.currentTimeMillis()+"&regSource=&regSourceType=")
					.addHeader("TE", "Trailers")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("isRegAccount\":true")) {
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
