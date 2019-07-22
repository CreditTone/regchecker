package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@UsePapaSpider
public class ZhongLiangSpider extends PapaSpider {
	
	@Override
	public String message() {
		return "中粮集团旗下食品平台，家庭一站式购物体验，足不出户，让您体验全球优质食品!依托中粮全球产业链，形成了以生鲜果蔬、进口食品、肉禽水产、粮油米面及自有品牌为核心的优质食品提供商，并打造家庭消费为概念的生活方式,让您吃的放心。中粮我买-为中国家庭提供优质食品!";
	}

	@Override
	public String platform() {
		return "womai";
	}

	@Override
	public String home() {
		return "womai.com";
	}

	@Override
	public String platformName() {
		return "中粮我买网";
	}

	@Override
	public String[] tags() {
		return new String[] {"购物" , "食品"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18779861101", "18210538513");
	}

	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
		headers.put("Host", "passport.m.womai.com");
		headers.put("Referer", "https://passport.m.womai.com/passport/login.action");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return Headers.of(headers);
	}
	
	private String getImgCode() {
		Response response;
		String vcode = null;
		try {
			String imageUrl = "https://passport.m.womai.com/passport/generateIdentifyCode.action?codeType=0&r=0.09"+System.currentTimeMillis();
			Request request = new Request.Builder().url(imageUrl)
        			.headers(getHeader())
					.build();
			response = okHttpClient.newCall(request).execute();
			byte[] body = response.body().bytes();
			vcode = OCRDecode.decodeImageCode(body);
			return vcode;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			get("https://passport.m.womai.com/passport/login.action").body().string();
			String url = "https://passport.m.womai.com/passport/login.action";
			for (int i = 0 ; i < 5 ; i ++) {
				String code = getImgCode();
				FormBody formBody = new FormBody
		                .Builder()
		                .add("username", account)
						.add("password", "ckajk2mcjoos")
						.add("_identifyingCode", code)
						.add("identifyingCode", code)
						.add("codeType", "0")
						.add("mp", "")
		                .build();
				Request request = new Request.Builder().url(url)
	        			.headers(getHeader())
	        			.post(formBody)
						.build();
				Response response = okHttpClient.newCall(request).execute();
				String res = response.body().string();
				Document doc = Jsoup.parse(res);
				String error = doc.select(".m-error").text();
				if (error.contains("验证码错误")) {
					continue;
				}
				if (error.contains("不存在")) {
					return false;
				}
				if (error.contains("密码错误")) {
					return true;
				}
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
