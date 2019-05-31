package com.jisucloud.clawler.regagent.service.impl;

import com.jisucloud.clawler.regagent.service.CookieService;
import com.jisucloud.clawler.regagent.service.Cookies;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.PersistenceCookieJar;
import com.jisucloud.clawler.regagent.util.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class BenlaiShenghuoSpider implements PapaSpider {
	
	private PersistenceCookieJar persistenceCookieJar = new PersistenceCookieJar();

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.cookieJar(persistenceCookieJar)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "benlai";
	}

	@Override
	public String platform() {
		return "benlai";
	}

	@Override
	public String home() {
		return "benlai.com";
	}

	@Override
	public String platformName() {
		return "本来生活网";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("电商", new String[] { });
			}
		};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new BenlaiShenghuoSpider().checkTelephone("13879691485"));
		System.out.println(new BenlaiShenghuoSpider().checkTelephone("18210538513"));
		System.out.println(new BenlaiShenghuoSpider().checkTelephone("18210538511"));
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			Cookies cookies = CookieService.getInstance().getCookies(CookieService.BENLAI_COOKIE);
			persistenceCookieJar.saveCookies(cookies);
			Request request = new Request.Builder().url("https://www.benlai.com/login.html")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.benlai.com")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			Document doc = Jsoup.parse(response.body().string());
			String requestVerificationToken = doc.select("input[name=__RequestVerificationToken]").first().attr("value");
			FormBody formBody = new FormBody
	                .Builder()
	                .add("__RequestVerificationToken", requestVerificationToken)
	                .add("txtFrom", "")
	                .add("txtJiZhuUser1", "1")
	                .add("txtJiZhuUserPass1", "1")
	                .add("txtLoginUid1", account)
	                .add("txtLoginPwd1", "841baaa3fee204e24965fc3272336933")
	                .add("checkboxUserRegisterContent", "on")
	                .build();
			request = new Request.Builder().url("https://www.benlai.com/Account/Login?r=1365&_="+(System.currentTimeMillis() / 1000))
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.benlai.com")
					.addHeader("Referer", "https://www.benlai.com/login.html")
					.post(formBody)
					.build();
			response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			System.out.println(res);
			if (res.contains("密码不正确")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
