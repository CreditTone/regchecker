package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DeHongPuHuiSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "德鸿普惠综合金融服务商,是通过“互联网+金融”战略计划,依托移动互联网大数据为基础的,为微小企业和个人提供小额的金融借贷服务普惠金融的平台。";
	}

	@Override
	public String platform() {
		return "dhibank";
	}

	@Override
	public String home() {
		return "dhibank.com";
	}

	@Override
	public String platformName() {
		return "德鸿普惠";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new DeHongPuHuiSpider().checkTelephone("15985268904"));
//		System.out.println(new DeHongPuHuiSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.dhibank.net/rest/api/app/user/appLogin";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json;charset=UTF-8"), "{\"customerType\":1001,\"registerType\":1017,\"password\":\"89b9168e84bad294037805f8014d5384\",\"username\":\""+account+"\",\"client\":2}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", ANDROID_USER_AGENT)
					.addHeader("Host", "m.dhibank.net")
					.addHeader("CustomHead", "{\"client\":2,\"customerType\":\"1001\",\"registerType\":\"1017\"}")
					.addHeader("Referer", "https://m.dhibank.net/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("密码错误") || res.contains("锁定")) {
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
