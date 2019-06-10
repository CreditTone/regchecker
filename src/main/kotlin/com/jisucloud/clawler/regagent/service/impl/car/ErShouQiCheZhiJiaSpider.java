package com.jisucloud.clawler.regagent.service.impl.car;

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
public class ErShouQiCheZhiJiaSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "二手车之家二手车,提供二手车报价,众多二手车商家入驻,二手车源信息真实完善,选车放心、便捷,是最大的二手车交易市场,买卖二手车就上二手车之家。";
	}

	@Override
	public String platform() {
		return "che168";
	}

	@Override
	public String home() {
		return "che168.com";
	}

	@Override
	public String platformName() {
		return "二手车之家";
	}

	@Override
	public String[] tags() {
		return new String[] {"二手车", "卖车", "买车"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ErShouQiCheZhiJiaSpider().checkTelephone("13879691485"));
//		System.out.println(new ErShouQiCheZhiJiaSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.che168.com/AccountApi/CheckPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone",account)
	                .add("isOverSea", "0")
	                .add("validcodetype", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.che168.com")
					.addHeader("Referer", "https://account.che168.com/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("0")) {
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
