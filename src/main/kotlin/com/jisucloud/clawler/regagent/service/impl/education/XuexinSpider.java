package com.jisucloud.clawler.regagent.service.impl.education;

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
public class XuexinSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "光宇游戏国内十大网络游戏运营商之一，百万级玩家在线游戏平台，同时也是知名的网络游戏研发公司。";
	}

	@Override
	public String platform() {
		return "gyyx";
	}

	@Override
	public String home() {
		return "gyyx.com";
	}

	@Override
	public String platformName() {
		return "光宇游戏";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("娱乐", new String[] { "游戏" });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new XuexinSpider().checkTelephone("13879691485"));
//		System.out.println(new XuexinSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://reg.gyyx.cn/register/CheckPhoneAccountIsExist?jsoncallback=jQuery5086078795_"+System.currentTimeMillis()+"&userName="+account+"&r=0.4185677471204179&_="+System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "reg.gyyx.cn")
					.addHeader("Referer", "http://account.gyyx.cn/Member/RegisterPhone?gameId=0&toURL=http%3A%2F%2Fwww.gyyx.cn%2F&reg_mobreg=")
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
