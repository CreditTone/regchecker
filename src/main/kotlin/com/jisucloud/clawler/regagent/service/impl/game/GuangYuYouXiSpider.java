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
//@Component
public class GuangYuYouXiSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "教育部学历查询网站、教育部高校招生阳光工程指定网站、全国硕士研究生招生报名和调剂指定网站。";
	}

	@Override
	public String platform() {
		return "chdsadasdassi";
	}

	@Override
	public String home() {
		return "asdas.com";
	}

	@Override
	public String platformName() {
		return "学dasdasdasd信网";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("教育dasdas", new String[] { "dasd" });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new XuexinSpider().checkTelephone("13879691485"));
//		System.out.println(new XuexinSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.chsi.com.cn/account/checkmobilephoneother.action";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mphone",account)
	                .add("dataInfo",account)
	                .add("optType","REGISTER")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.chsi.com.cn")
					.addHeader("Referer", "https://account.chsi.com.cn/account/preregister.action?from=archive")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("false")) {
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
