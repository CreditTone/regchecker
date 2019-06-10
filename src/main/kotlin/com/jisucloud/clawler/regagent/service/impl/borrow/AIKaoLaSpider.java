package com.jisucloud.clawler.regagent.service.impl.borrow;

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
public class AIKaoLaSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "AI考拉是隶属于广州财略金融信息科技有限公司的互联网金融软件，有iOS和Android版app。2018年02月11日，软件由原有的考拉理财升级为AI考拉。";
	}

	@Override
	public String platform() {
		return "kaolalicai";
	}

	@Override
	public String home() {
		return "kaolalicai.com";
	}

	@Override
	public String platformName() {
		return "AI考拉";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "征信" , "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new AIKaoLaSpider().checkTelephone("18611216720"));
//		System.out.println(new AIKaoLaSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://app.kaolalicai.cn/api/v2/user/isRegister?os=wap&longTtl=true&phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "app.kaolalicai.cn")
					.addHeader("Referer", "https://app.kaolalicai.cn/msite/login?chnl=website&redirect_uri=/msite/guanwang_note_detail")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("isRegister\": true") || res.contains("isRegister\":true")) {
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
