package com.jisucloud.clawler.regagent.service.impl.music;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class XiMaLaYaSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "喜马拉雅，颠覆了传统电台、个人网络电台单调的在线收听模式，让人们不仅能随时随地，听我想听，更能够轻松创建个人电台，随时分享好声音。在喜马拉雅，你随手就能上传声音作品，创建一个专属于自己的个人电台，持续发展积累粉丝并始终和他们连在一起。";
	}

	@Override
	public String platform() {
		return "ximalaya";
	}

	@Override
	public String home() {
		return "ximalaya.com";
	}

	@Override
	public String platformName() {
		return "喜马拉雅";
	}

	@Override
	public String[] tags() {
		return new String[] {"听书", "生活休闲"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new XiMaLaYaSpider().checkTelephone("18210538500"));
//		System.out.println(new XiMaLaYaSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.ximalaya.com/passport/register/checkAccount?account=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.ximalaya.com")
					.addHeader("Referer", "https://www.ximalaya.com/passport/register")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已注册")) {
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
