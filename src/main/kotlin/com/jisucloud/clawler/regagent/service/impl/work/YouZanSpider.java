package com.jisucloud.clawler.regagent.service.impl.work;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class YouZanSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "有赞是一个商家服务公司，我们帮助每一位重视产品和服务的商家成功。目前旗下拥有：有赞微商城、有赞零售、有赞教育、有赞美业、有赞小程序等SaaS软件产品，适用全行业多场景，帮商家网上开店、网上营销、管理客户、获取订单。";
	}

	@Override
	public String platform() {
		return "youzan";
	}

	@Override
	public String home() {
		return "youzan.com";
	}

	@Override
	public String platformName() {
		return "有赞";
	}

	@Override
	public String[] tags() {
		return new String[] {"saas", "生意"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new YouZanSpider().checkTelephone("13043603313"));
//		System.out.println(new YouZanSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://uic.youzan.com/buyer/auth/authConfirm.json";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("code", "")
	                .add("source", "13")
	                .add("password", "")
	                .add("country_code", "+86")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "uic.youzan.com")
					.addHeader("Referer", "https://h5.youzan.com/wscuser/account/shop-registry#")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("请登录")) {
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
