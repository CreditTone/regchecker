package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class ShouJinWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "首金网是由首都金融服务商会牵头,联合首钢、首创等企业共同注资打造的北京市小微企业综合金融服务电子交易平台,是中国互联网金融协会理事单位。旨在以互联网金融的创新。";
	}

	@Override
	public String platform() {
		return "shoujinwang";
	}

	@Override
	public String home() {
		return "shoujinwang.com";
	}

	@Override
	public String platformName() {
		return "首金网";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ShouJinWangSpider().checkTelephone("15985268904"));
//		System.out.println(new ShouJinWangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.shoujinwang.com/systLogonUser/checkUserPhoneState.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("info", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.shoujinwang.com")
					.addHeader("Referer", "https://www.shoujinwang.com/systLogonUser/forgetPass.do")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return result.getBooleanValue("isHad");
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
