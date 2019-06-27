package com.jisucloud.clawler.regagent.service.impl.work;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ChinahrSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "中华英才网北京招聘网,为您提供北京招聘,北京找工作,北京人才网,北京求职信息,同时覆盖校园招聘、求职指导、职业测评、猎头服务等求职服务。";
	}

	@Override
	public String platform() {
		return "chinahr";
	}

	@Override
	public String home() {
		return "chinahr.com";
	}

	@Override
	public String platformName() {
		return "中华英才网";
	}

	@Override
	public String[] tags() {
		return new String[] {"求职" , "招聘"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ChinahrSpider().checkTelephone("18210538513"));
//		System.out.println(new ChinahrSpider().checkTelephone("18210538511"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://passport.chinahr.com/ajax/m/existLoginName?input="+account+"&_=" +System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.chinahr.com")
					.addHeader("Referer", "http://passport.chinahr.com/pc/toregister?backUrl=%2F%2Fwww.chinahr.com&from=")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("用户存在")) {
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
