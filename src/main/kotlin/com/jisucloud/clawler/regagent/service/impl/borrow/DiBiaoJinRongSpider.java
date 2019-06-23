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
public class DiBiaoJinRongSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "地标金融国内首批由第三方做资金托管账户的P2P网贷平台,网贷投资专业首选—地标金融。地标金融网络借贷平台,线上P2P投资方便快捷!";
	}

	@Override
	public String platform() {
		return "dib66";
	}

	@Override
	public String home() {
		return "dib66.com";
	}

	@Override
	public String platformName() {
		return "地标金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new DiBiaoJinRongSpider().checkTelephone("15985268904"));
		System.out.println(new DiBiaoJinRongSpider().checkTelephone("18210538513"));
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.dib66.com/ajaxCheckLog.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("param", account)
	                .add("name", "paramMap.email")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.dib66.com")
					.addHeader("Referer", "https://www.dib66.com/secure/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("\"y\"")) {
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
