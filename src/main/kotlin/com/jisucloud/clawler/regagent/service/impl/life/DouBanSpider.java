package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class DouBanSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "豆瓣（douban）是一个社区网站。网站由杨勃（网名“阿北”） 创立于2005年3月6日。该网站以书影音起家，提供关于书籍、电影、音乐等作品的信息，无论描述还是评论都由用户提供（User-generated content，UGC），是Web 2.0网站中具有特色的一个网站。";
	}

	@Override
	public String platform() {
		return "douban";
	}

	@Override
	public String home() {
		return "douban.com";
	}

	@Override
	public String platformName() {
		return "豆瓣";
	}

	@Override
	public String[] tags() {
		return new String[] {"社区", "影音" , "阅读"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new DouBanSpider().checkTelephone("13844441111"));
//		System.out.println(new DouBanSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://accounts.douban.com/j/mobile/reset_password/request_phone_code";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("number", account)
	                .add("ck", "")
	                .add("area_code", "+86")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "accounts.douban.com")
					.addHeader("Referer", "https://accounts.douban.com/passport/get_password")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("success")) {
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
