package com.jisucloud.clawler.regagent.service.impl.shop;

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
public class LiangPinPuZiSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "良品铺子，12年专注高端零食，精选全球32大产地食材，全国门店2000多家。2015年起，连续3年全国销售领先。（数据来源商务部流通产业促进中心2018年《消费升级背景下零食行业发展报告》）良品铺子产品超过1000种，口味丰富多样，尤其是十二经典产品，获得顾客热烈追捧。";
	}

	@Override
	public String platform() {
		return "lppz";
	}

	@Override
	public String home() {
		return "lppz.com";
	}

	@Override
	public String platformName() {
		return "良品铺子";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("媒体", new String[] { });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new LiangPinPuZiSpider().checkTelephone("18515290717"));
//		System.out.println(new LiangPinPuZiSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://mappssl.lppz.com/services/user/userExisted";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("body", "{\"phoneNumber\":"+account+"}")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "mappssl.lppz.com")
					.addHeader("Referer", "http://sns.lppz.com/sign/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已经存在用户")) {
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
