package com.jisucloud.clawler.regagent.service.impl.health;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class IKangGuoBinSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "爱康国宾体检中心是全国知名体检中心,是拥有多年健康体检及健康管理经验的专业综合性健康管理机构;提供健康体检(男体检/女性体检)、健康检查。";
	}

	@Override
	public String platform() {
		return "ikang";
	}

	@Override
	public String home() {
		return "ikang.com";
	}

	@Override
	public String platformName() {
		return "爱康国宾";
	}

	@Override
	public String[] tags() {
		return new String[] {"健康运动", "医疗", "体检"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15901537458", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://oauth2.health.ikang.com/register/request";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Chunyuyisheng/8.6.0 (Android 4.4.2;8692-A00_by_QiKU)")
					.addHeader("Host", "oauth2.health.ikang.com")
					.addHeader("Referer", "https://oauth2.health.ikang.com/register1?response_type=token&client_id=6237cf0f-b603-4246-8f81-88a64b0c889b&state=xyz&channel_id=h5&channel_name=h5_mobile&type_3rd=alipay&redirect_uri=http://m.ikang.com/auth.html&m=1")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已经被使用")) {
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
