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
public class MingPianQuanNengWangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "名片全能王,名片识别、管理的专业工具:手机拍摄名片,信息瞬间存入通讯录,用名片呈现图文、语音、视频、附件,精准识别16种语言的名片!";
	}

	@Override
	public String platform() {
		return "camcard";
	}

	@Override
	public String home() {
		return "camcard.com";
	}

	@Override
	public String platformName() {
		return "名片全能王";
	}

	@Override
	public String[] tags() {
		return new String[] {"工具" };
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15011488781");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.camcard.com/user/auth";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .add("act", "login")
	                .add("password", "sada213dasd")
	                .add("redirect", "")
	                .add("next_login", "0")
	                .add("from", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", " Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "www.camcard.com")
					.addHeader("Referer", "https://www.camcard.com/user/login")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (!res.contains("-201")) {
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
