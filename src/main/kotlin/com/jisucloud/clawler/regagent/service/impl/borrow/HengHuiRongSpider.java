package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.http.PersistenceCookieJar;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

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
public class HengHuiRongSpider implements PapaSpider {

	private PersistenceCookieJar persistenceCookieJar = new PersistenceCookieJar();
	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.cookieJar(persistenceCookieJar)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "恒慧融(henghuirong.com)是由恒盛致远(北京)金融信息服务有限公司设立,通过互联网实现直接借贷的网络借贷信息中介平台。";
	}

	@Override
	public String platform() {
		return "henghuirong";
	}

	@Override
	public String home() {
		return "henghuirong.com";
	}

	@Override
	public String platformName() {
		return "恒慧融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new HuiYIngJInFuSpider().checkTelephone("13910252045"));
//		System.out.println(new HuiYIngJInFuSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		String img = "http://loan.henghuirong.com/captcha.html?d=0.6"+System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			try {
				byte[] body = okHttpClient.newCall(new Request.Builder().url(img).build()).execute().body().bytes();
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://loan.henghuirong.com/fpwd1Ajax";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("img_code", getImgCode())
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "loan.henghuirong.com")
					.addHeader("Referer", "http://loan.henghuirong.com/fpwd1")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("true")) {
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
