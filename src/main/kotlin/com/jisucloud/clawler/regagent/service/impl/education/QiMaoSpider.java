package com.jisucloud.clawler.regagent.service.impl.education;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class QiMaoSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "七猫免费小说是一款提供小说阅读服务的软件。小说内容覆盖了总裁豪门小说、言情小说、穿越架空小说、玄幻小说、青春校园小说、修仙小说、悬疑小说、同人小说、名著等各种类型。";
	}

	@Override
	public String platform() {
		return "kimao";
	}

	@Override
	public String home() {
		return "km.com";
	}

	@Override
	public String platformName() {
		return "七猫小说";
	}

	@Override
	public String[] tags() {
		return new String[] {"小说","电子书"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new QiMaoSpider().checkTelephone("18779861101"));
//		System.out.println(new QiMaoSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			Request request = new Request.Builder().url("https://i.km.com/api/getPassidByPhone?phone="+account+"&callback=jQuery183047210586920&_=" + System.currentTimeMillis())
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "i.km.com")
					.addHeader("Referer", "https://book.km.com/index.php?c=login&a=reg&forward=https%3A%2F%2Fbook.km.com%2F")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("passid\":1")) {
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
