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
public class EnKeEDaiSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "恩科e贷是一家专注于不动产抵押的P2P网贷平台。打造河北P2P网贷平台、石家庄P2P网贷平台及金融供应链金融,互联网金融服务。致力于引领和推进行业进步,合国内市场的。";
	}

	@Override
	public String platform() {
		return "ek360";
	}

	@Override
	public String home() {
		return "ek360.com";
	}

	@Override
	public String platformName() {
		return "恩科e贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P" , "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new EnKeEDaiSpider().checkTelephone("15985268904"));
//		System.out.println(new EnKeEDaiSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.ek360.com/member/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("password", "maoab2Q11ile")
	                .add("", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.ek360.com")
					.addHeader("Referer", "https://www.ek360.com/#/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码错误")) {
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
