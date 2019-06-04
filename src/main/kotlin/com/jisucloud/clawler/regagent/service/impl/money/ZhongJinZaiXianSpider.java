package com.jisucloud.clawler.regagent.service.impl.money;

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
public class ZhongJinZaiXianSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "中金在线-中国人的金融门户网站,覆盖财经、股票、 证券、金融、港股、行情、基金、债券、期货、外汇、保险、银行、博客、股票分析软件等多种面向个人和企业的服务。";
	}

	@Override
	public String platform() {
		return "cnfol";
	}

	@Override
	public String home() {
		return "cnfol.com";
	}

	@Override
	public String platformName() {
		return "中金在线";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("金融", new String[] { "储蓄"});
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhongJinZaiXianSpider().checkTelephone("18210538000"));
//		System.out.println(new ZhongJinZaiXianSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.cnfol.com/userregister/ajaxcheckmobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("type", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.cnfol.com")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("Referer", "https://passport.cnfol.com/userregister?rt=aHR0cHM6Ly9wYXNzcG9ydC5jbmZvbC5jb20v")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			System.out.println(res);
			if (res.contains("\\u53f7\\u5df2\\u88ab\\u6ce8\\u518c") || res.contains("已被注册")) {
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
