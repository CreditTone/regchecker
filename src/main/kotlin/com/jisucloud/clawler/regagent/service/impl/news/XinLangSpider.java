package com.jisucloud.clawler.regagent.service.impl.news;

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
public class XinLangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "新浪网为全球用户24小时提供全面及时的中文资讯，内容覆盖国内外突发新闻事件、体坛赛事、娱乐时尚、产业资讯、实用信息等，设有新闻、体育、娱乐、财经、科技、房产、汽车等30多个内容频道，同时开设博客、视频、论坛等自由互动交流空间。";
	}

	@Override
	public String platform() {
		return "sina";
	}

	@Override
	public String home() {
		return "sina.com";
	}

	@Override
	public String platformName() {
		return "新浪网";
	}

	@Override
	public String[] tags() {
		return new String[] {"新闻资讯"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new XinLangSpider().checkTelephone("18210538000"));
//		System.out.println(new XinLangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://login.sina.com.cn/signup/check_user.php";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("name", account)
	                .add("format", "json")
	                .add("from", "mobile")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "login.sina.com.cn")
					.addHeader("Referer", "https://login.sina.com.cn/signup/signup?entry=homepage")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("100001")) {
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
