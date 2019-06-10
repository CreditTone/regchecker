package com.jisucloud.clawler.regagent.service.impl.health;

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
public class PharmacySpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "澳洲PO药房是澳洲本土大型综合类药房,也是首家开通直邮中国服务的澳洲本土药房,提供数万母婴用品、保健品、护肤化妆品、洗护用品等各类产品选购,全中文购物界面。";
	}

	@Override
	public String platform() {
		return "pharmacyonline";
	}

	@Override
	public String home() {
		return "pharmacyonline.com";
	}

	@Override
	public String platformName() {
		return "澳洲PO药房";
	}

	@Override
	public String[] tags() {
		return new String[] {"购药", "用药"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new YinLianShangWuSpider().checkTelephone("18210538000"));
//		System.out.println(new YinLianShangWuSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://cn.pharmacyonline.com.au/api/member/check?username=" + account + "&method=get&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "cn.pharmacyonline.com.au")
					.addHeader("Referer", "https://cn.pharmacyonline.com.au/security/passwordforgotten?step=account")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (!response.body().string().contains("用户不存在")) {
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
