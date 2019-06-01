package com.jisucloud.clawler.regagent.service.impl;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class BaiXingWangSpider implements PapaSpider {
	
	private Session session = JJsoup.newSession();
	
	private String token;
	private String token2Value;
	private String token2Name;

	@Override
	public String message() {
		return "百姓网，最大的分类信息网。您可以免费查找最新最全的二手物品交易、二手车买卖、房屋租售、宠物、招聘、兼职、求职、交友活动及生活服务等分类信息，还能免费发布这些分类信息。";
	}

	@Override
	public String platform() {
		return "baixing";
	}

	@Override
	public String home() {
		return "baixing.com";
	}

	@Override
	public String platformName() {
		return "百姓网";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("借贷", new String[] {"p2p" });
			}
		};
	}

//	public static void main(String[] args) {
//		System.out.println(new BaiXingWangSpider().checkTelephone("18210538513"));
//		System.out.println(new BaiXingWangSpider().checkTelephone("18210538577"));
//	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "passport.ppdai.com");
		headers.put("Referer", "https://passport.ppdai.com/resetPassword.html");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private void getTokenCode() {
		Connection.Response response;
		String imageCodeUrl = "http://www.baixing.com/oz/login###";
		try {
			response = session.connect(imageCodeUrl)
					.execute();
			Document doc = Jsoup.parse(response.body());
			token = doc.select("input[name=token]").attr("value");
			token2Name = doc.select("input[name=token] ~ input").attr("name");
			token2Value = doc.select("input[name=token] ~ input").attr("value");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			getTokenCode();
			String url = "http://www.baixing.com/oz/login###";
			Connection.Response response = session.connect(url)
					.method(Method.POST)
					.data("identity", account)
					.data("token", token)
					.data(token2Name, token2Value)
					.data("password", "woxaomia133")
					.headers(getHeader()).ignoreContentType(true).execute();
			Document doc = Jsoup.parse(response.body());
			if (doc.select(".alert-error").text().contains("密码错误")) {
				return true;
			}else {
				return false;
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
