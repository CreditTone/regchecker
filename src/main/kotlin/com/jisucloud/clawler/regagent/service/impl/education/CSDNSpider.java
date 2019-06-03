package com.jisucloud.clawler.regagent.service.impl.education;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CSDNSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "中国专业IT社区CSDN (Chinese Software Developer Network) 创立于1999年，致力于为中国软件开发者提供知识传播、在线学习、职业发展等全生命周期服务。";
	}

	@Override
	public String platform() {
		return "csdn";
	}

	@Override
	public String home() {
		return "csdn.net";
	}

	@Override
	public String platformName() {
		return "CSDN";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("教育", new String[] { "IT" });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new CSDNSpider().checkTelephone("13879691485"));
//		Thread.sleep(3000);
//		System.out.println(new CSDNSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.csdn.net/v1/service/mobiles/"+account+"?comeFrom=0&code=0086";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.csdn.net")
					.addHeader("Referer", "https://passport.csdn.net/forget")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response != null) {
				JSONObject result = JSON.parseObject(response.body().string());
				log.info("csdn:" + result);
				if (result.getBooleanValue("status")) {
					return true;
				}
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
