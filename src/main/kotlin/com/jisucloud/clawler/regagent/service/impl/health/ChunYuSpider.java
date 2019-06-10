package com.jisucloud.clawler.regagent.service.impl.health;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ChunYuSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "春雨医生提供真实医生的在线医疗健康咨询服务。由公立医院医师解答用户的健康问题。";
	}

	@Override
	public String platform() {
		return "chunyuyisheng";
	}

	@Override
	public String home() {
		return "chunyuyisheng.com";
	}

	@Override
	public String platformName() {
		return "春雨医生";
	}

	@Override
	public String[] tags() {
		return new String[] {"健康运动", "医疗", "生活应用" , "挂号" , "用药"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ChunYuSpider().checkTelephone("13910252045"));
//		System.out.println(new ChunYuSpider().checkTelephone("13910252040"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://api.chunyuyisheng.com/api/accounts/is_registered/?app=0&client=me.chunyu.ChunyuDoctor&platform=android&version=8.6.0&app_ver=8.6.0&imei=352284040670808&device_id=352284040670808&phoneType=8692-A00_by_QiKU&vendor=anzhi";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Chunyuyisheng/8.6.0 (Android 4.4.2;8692-A00_by_QiKU)")
					.addHeader("Host", "api.chunyuyisheng.com")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("is_registered\": true") || res.contains("is_registered\":true")) {
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
