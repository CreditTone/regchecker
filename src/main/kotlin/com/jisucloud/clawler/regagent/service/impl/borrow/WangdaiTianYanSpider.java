package com.jisucloud.clawler.regagent.service.impl.borrow;
//package com.jisucloud.clawler.regagent.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.jisucloud.clawler.regagent.service.PapaSpider;
//import com.jisucloud.clawler.regagent.util.OCRDecode;
//
//import lombok.extern.slf4j.Slf4j;
//import me.kagura.JJsoup;
//import me.kagura.Session;
//
//import org.jsoup.Connection;
//import org.jsoup.Connection.Method;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//@Component
//@Slf4j
//public class WangdaiTianYanSpider implements PapaSpider {
//
//	@Override
//	public String message() {
//		return "网贷天眼资讯频道为用户提供网贷新闻头条、p2p理财网贷资讯,互联网金融行业新闻,包含有网贷政策、网贷行业、平台动态、行业观点、互联网金融等内容.";
//	}
//
//	@Override
//	public String platform() {
//		return "p2peye";
//	}
//
//	@Override
//	public String home() {
//		return "p2peye.com";
//	}
//
//	@Override
//	public String platformName() {
//		return "网贷天眼";
//	}
//
//	@Override
//	public Map<String, String[]> tags() {
//		return new HashMap<String, String[]>() {
//			{
//				put("金融理财", new String[] { "借贷", "贷超" });
//			}
//		};
//	}
//
////	public static void main(String[] args) {
////		System.out.println(new WangdaiTianYanSpider().checkTelephone("18210538513"));
////		System.out.println(new WangdaiTianYanSpider().checkTelephone("18210538577"));
////	}
//
//	private Map<String, String> getHeader() {
//		Map<String, String> headers = new HashMap<>();
//		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
//		headers.put("Host", "www.p2peye.com");
//		headers.put("Referer", "https://www.p2peye.com/member.php?mod=getpwd");
//		headers.put("X-Requested-With", "XMLHttpRequest");
//		return headers;
//	}
//
//	private Map<String, String> getParams(String mobile) {
//		Map<String, String> params = new HashMap<>();
//		params.put("type", "get_phone_code");
//		params.put("phonenum", mobile);
//		return params;
//	}
//
//	private String getImgCode(Session session) {
//		String img = "https://www.p2peye.com/misc.php?mod=seccode&update=" + new Random().nextInt(10000) + "&idhash=cS";
//		Connection.Response response;
//		for (int i = 0; i < 10; i++) {
//			try {
//				response = session.connect(img)
//						.headers(getHeader())
//						.execute();
//				if (response != null) {
//					byte[] body = response.bodyAsBytes();
//					String imageCode = OCRDecode.decodeImageCodeForChinese(body);
//					if (imageCode != null) {
//						String checkUrl = "https://www.p2peye.com/misc.php?mod=seccode&action=check&inajax=1&modid=member::getpwd&idhash=cS&secverify="
//								+ URLEncoder.encode(imageCode);
//						Connection.Response checkresponse = session.connect(checkUrl).execute();
//						if (checkresponse.body().contains("succeed")) {
//							return imageCode;
//						}
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public boolean checkTelephone(String account) {
//		try {
//			Session session = JJsoup.newSession();
////			String imgcode = getImgCode(session);
//			String url = "https://www.p2peye.com/member.php?mod=phonecode";
//			System.out.println(url);
//			Connection.Response response = session.connect(url)
//					.method(Method.POST)
//					.data(getParams(account))
//					.headers(getHeader()).ignoreContentType(true).execute();
//			if (response != null) {
//				JSONObject result = JSON.parseObject(response.body());
//				return result.getString("message").equals("OK");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	@Override
//	public boolean checkEmail(String account) {
//		return false;
//	}
//
//	@Override
//	public Map<String, String> getFields() {
//		return null;
//	}
//
//}
