package com.jisucloud.clawler.regagent.service.impl.trip;

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
public class CaissaSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "凯撒旅游创始于1993年，经过20多年的稳健发展，相继在伦敦、巴黎、汉堡、洛杉矶等全球核心城市设有分支机构，在中国北京、广州、上海、成都以及沈阳等口岸城市和核心商业城市设有30余家分子公司。";
	}

	@Override
	public String platform() {
		return "caissa";
	}

	@Override
	public String home() {
		return "caissa.com";
	}

	@Override
	public String platformName() {
		return "凯撒旅游网";
	}

	@Override
	public String[] tags() {
		return new String[] {"旅游" , "酒店" , "美食" , "o2o"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new CaissaSpider().checkTelephone("18515290717"));
//		System.out.println(new CaissaSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://my.caissa.com.cn/Registered/CheckPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("hdurl", "http://my.caissa.com.cn/")
	                .add("hdsessionId", "")
	                .add("imgcode", "")
	                .add("captcha", "")
	                .add("password", "")
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "my.caissa.com.cn")
					.addHeader("Referer", "http://my.caissa.com.cn/Registered/index")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已存在")) {
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
