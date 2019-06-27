package com.jisucloud.clawler.regagent.service.impl.car;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

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
@UsePapaSpider
public class NiuCheWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "牛车网于2013年3月成立于北京，隶属于耐卡（香港）有限公司，前身是NextCar。牛车网立志成长为汽车第一意见网站，打造最庞大的汽车牛人团队、成长为汽车互联网第一社区。";
	}

	@Override
	public String platform() {
		return "niuche";
	}

	@Override
	public String home() {
		return "niuche.com";
	}

	@Override
	public String platformName() {
		return "牛车网";
	}

	@Override
	public String[] tags() {
		return new String[] {"买车" , "汽车"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new NiuCheWangSpider().checkTelephone("18210538511"));
//		System.out.println(new NiuCheWangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://user.niuche.com/reg/api/checkPhone.ashx";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone",account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "user.niuche.com")
					.addHeader("Referer", "https://user.niuche.com/user/reg.html?gourl=http://www.niuche.com/")
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
