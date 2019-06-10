package com.jisucloud.clawler.regagent.service.impl.game;

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
public class KongzhongWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "空中网是中国领先的网络游戏研发商和运营商,致力于为中国及海外互联网用户提供高品质的大型在线游戏服务,同时为中国手机用户提供多元化的无线娱乐服务。";
	}

	@Override
	public String platform() {
		return "kongzhong";
	}

	@Override
	public String home() {
		return "kongzhong.com";
	}

	@Override
	public String platformName() {
		return "空中网";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new KongzhongWangSpider().checkTelephone("13800000000"));
//		System.out.println(new KongzhongWangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.kongzhong.com/ajax/regcheckphone?clientid=useraccount&rand="+System.currentTimeMillis()+"&useraccount="+account+"&vcode=&personid=&bdFlag=0&phone="+account+"&_="+System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.kongzhong.com")
					.addHeader("Referer", "https://passport.kongzhong.com/acc")
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
