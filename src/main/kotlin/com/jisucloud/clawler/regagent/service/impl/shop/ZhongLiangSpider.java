package com.jisucloud.clawler.regagent.service.impl.shop;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection.Method;

@Slf4j
@UsePapaSpider
public class ZhongLiangSpider implements PapaSpider {
	
	private Session session = JJsoup.newSession();
	
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

//	public static void main(String[] args) {
//		System.out.println(new ZhongLiangSpider().checkTelephone("18779861101"));
//		System.out.println(new ZhongLiangSpider().checkTelephone("18210538577"));
//	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
		headers.put("Host", "passport.m.womai.com");
		headers.put("Referer", "https://passport.m.womai.com/passport/login.action");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private String getImgCode() {
		Connection.Response response;
		String vcode = null;
		try {
			String imageUrl = "https://passport.m.womai.com/passport/generateIdentifyCode.action?codeType=0&r=0.09"+System.currentTimeMillis();
			response = session.connect(imageUrl).headers(getHeader()).execute();
			byte[] body = response.bodyAsBytes();
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
			session.connect("https://passport.m.womai.com/passport/login.action").execute();
			String url = "https://passport.m.womai.com/passport/login.action";
			for (int i = 0 ; i < 5 ; i ++) {
				String code = getImgCode();
				Connection.Response response = session.connect(url)
						.method(Method.POST)
						.data("username", account)
						.data("password", "ckajk2mcjoos")
						.data("_identifyingCode", code)
						.data("identifyingCode", code)
						.data("codeType", "0")
						.data("mp", "")
						.headers(getHeader()).ignoreContentType(true).execute();
				String res = response.body();
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
