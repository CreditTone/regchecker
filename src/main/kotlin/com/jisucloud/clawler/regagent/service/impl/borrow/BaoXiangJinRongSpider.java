package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BaoXiangJinRongSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "宝象金融是集借款、理财于一体的综合性金融服务平台。是一家专注于农业供应链金融领域的互联网金融平台。宝象金融于2015年4月正式上线。";
	}

	@Override
	public String platform() {
		return "bxjr";
	}

	@Override
	public String home() {
		return "bxjr.com";
	}

	@Override
	public String platformName() {
		return "宝象金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new BaoXiangJinRongSpider().checkTelephone("15985268904"));
//		System.out.println(new BaoXiangJinRongSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.bxjr.com/validate/ajax-mobile.html";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("param", account)
	                .add("name", "mobile")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.bxjr.com")
					.addHeader("Referer", "https://www.bxjr.com/secure/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被使用")) {
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
