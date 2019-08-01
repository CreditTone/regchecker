package com.jisucloud.clawler.regagent.interfaces;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.deep007.spiderbase.DefaultHttpDownloader;
import com.deep007.spiderbase.okhttp.OKHttpUtil;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class PapaSpider {
	
	protected OkHttpClient okHttpClient;
	
	public PapaSpider() {
		okHttpClient = OKHttpUtil.createOkHttpClient();
	}
	
	protected final DefaultHttpDownloader createDefaultHttpDownloader() {
		return new DefaultHttpDownloader();
	}

	public static final Random RANDOM = new Random();

	public static final String ANDROID_USER_AGENT = "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36";

	public static final String IOS_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A402 Safari/604.1";

	public static final String CHROME_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0";

	public abstract String message();

	public abstract String platform();

	public abstract String home();

	public abstract String platformName();

	public abstract boolean checkTelephone(String account);

	public abstract boolean checkEmail(String account);

	public abstract Map<String, String> getFields();

	public abstract String[] tags();

	public abstract Set<String> getTestTelephones();

	public final void smartSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static RequestBody createUrlEncodedForm(String content) {
		return FormBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"), content);
	}
	
	public static RequestBody createJsonForm(String content) {
		return FormBody.create(MediaType.parse("application/json;charset=utf-8"), content);
	}
	
	public static Request createRequest(String url) {
		return new Request.Builder().url(url)
				.build();
	}
	
	protected Response get(String url) throws Exception {
		return okHttpClient.newCall(createRequest(url)).execute();
	}
}