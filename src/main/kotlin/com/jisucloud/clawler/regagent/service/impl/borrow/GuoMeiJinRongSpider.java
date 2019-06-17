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
public class GuoMeiJinRongSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "国美金融-国美控股旗下金融发展与投资业务战略管控平台,致力推动普惠金融的发展!国美金融,国美,家美,生活美!";
	}

	@Override
	public String platform() {
		return "gomefinance";
	}

	@Override
	public String home() {
		return "gomefinance.com";
	}

	@Override
	public String platformName() {
		return "国美金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new GuoMeiJinRongSpider().checkTelephone("15985268904"));
//		System.out.println(new GuoMeiJinRongSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.gomefinance.com.cn/api/v1/checkMobile?t=" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.gomefinance.com.cn")
					.addHeader("channel", "GMJRBDSSPPC")
					.addHeader("plant", "PC")
					.addHeader("Referer", "https://www.gomefinance.com.cn/src/html/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("该手机号已注册")) {
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
