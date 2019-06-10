package com.jisucloud.clawler.regagent.service.impl.life;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class ShuiDiXinYongSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "水滴信用,全国中小企业大数据信用评价平台,实时提供企业工商信息查询,企业信用查询,企业失信记录,企业对外投资信息,企业相关股东,法人等信息的查询。";
	}

	@Override
	public String platform() {
		return "shuidi";
	}

	@Override
	public String home() {
		return "shuidi.com";
	}

	@Override
	public String platformName() {
		return "水滴信用";
	}

	@Override
	public String[] tags() {
		return new String[] {"新闻咨询", "工具"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ShuiDiXinYongSpider().checkTelephone("15970663703"));
//		System.out.println(new ShuiDiXinYongSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://shuidi.cn/pcuser-register";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("action", "check_phone")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "shuidi.cn")
					.addHeader("Referer", "https://shuidi.cn/pcuser-register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getIntValue("status") == 1) {
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
