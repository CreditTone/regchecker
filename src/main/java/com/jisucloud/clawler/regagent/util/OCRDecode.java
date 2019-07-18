package com.jisucloud.clawler.regagent.util;

import java.net.Proxy;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class OCRDecode {
	/**
	 * 文字类型（n4：4位纯数字，n5：5位纯数字，n6:6位纯数字，e4：4位纯英文，e5：5位纯英文，e6：6位纯英文，ne4：4位英文数字，ne5：5位英文数字，ne6：6位英文数字），请准确填写，以免影响识别准确性。（其他类型，请搜索：通用文字识别-复杂版）
	 */
	
	private static OkHttpClient okHttpClient  = new OkHttpClient.Builder()
			.proxy(Proxy.NO_PROXY)
	        .connectTimeout(10, TimeUnit.SECONDS)
	        .readTimeout(60, TimeUnit.SECONDS)
	        .retryOnConnectionFailure(true)
	        .build();

	public static String decodeImageCode(byte[] image) {
		return decodeImageCode(image, "ne4");
	}
	
	public static String decodeImageCode(byte[] image, String type) {
		for (int i = 0; i < 3; i++) {
			try {
				FormBody formBody = new FormBody.Builder()
		                .add("v_pic", Base64.getEncoder().encodeToString(image))
		                .add("v_type", type)
		                .build();
				Request request = new Request.Builder()
			            .url("http://txyzmsb.market.alicloudapi.com/yzm")
			            .post(formBody)
			            .addHeader("Authorization", "APPCODE 10d26218b7124b28b068f70bae071348")
			            .build();
				Response response = okHttpClient.newCall(request).execute();
				String body = response.body().string();
				JSONObject result = JSON.parseObject(body);
				if (result != null && result.containsKey("errCode") && result.getIntValue("errCode") == 0) {
					log.debug("decode:"+result.getString("v_code"));
					return result.getString("v_code");
				}
				log.warn(result.toJSONString());
				if (body.contains("请咨询客服")) {
					break;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "abcd";
	}
	
	public static String decodeImageCode(String imageUrl) {
		try {
			Response response2 = okHttpClient.newCall(new Request.Builder().url(imageUrl).build()).execute();
			return decodeImageCode(response2.body().bytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "abcd";
	}
	
	public static String decodeImageCodeForChinese(byte[] image) {
		try {
			FormBody formBody = new FormBody.Builder()
	                .add("src", Base64.getEncoder().encodeToString(image))
	                .build();
			Request request = new Request.Builder()
		            .url("https://textreadplus.xiaohuaerai.com/textreadplus")
		            .post(formBody)
		            .addHeader("Authorization", "APPCODE 10d26218b7124b28b068f70bae071348")
		            .build();
			Response response = okHttpClient.newCall(request).execute();
			String body = response.body().string();
			JSONObject result = JSON.parseObject(body);
			if (result.containsKey("status") && result.getIntValue("status") == 200) {
				String chinese = "";
				Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]+").matcher(result.getString("msg"));
				while (matcher.find()) {
					chinese += matcher.group();
				}
				return !chinese.isEmpty()?chinese:null;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "abcd";
	}
	
	public static String decodeImageCodeForChinese(String imageUrl) {
		try {
			Response response2 = okHttpClient.newCall(new Request.Builder().url(imageUrl).build()).execute();
			return decodeImageCodeForChinese(response2.body().bytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "abcd";
	}
}
