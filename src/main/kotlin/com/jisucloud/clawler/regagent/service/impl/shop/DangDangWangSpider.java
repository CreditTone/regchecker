package com.jisucloud.clawler.regagent.service.impl.shop;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.springframework.util.FileCopyUtils;

@Slf4j
public class DangDangWangSpider implements PapaSpider {
	
	private Session session = JJsoup.newSession();
	
	@Override
	public String message() {
		return "当当是知名的综合性网上购物商城，由国内著名出版机构科文公司、美国老虎基金、美国IDG集团、卢森堡剑桥集团、亚洲创业投资基金（原名软银中国创业基金）共同投资成立。";
	}

	@Override
	public String platform() {
		return "dangdang";
	}

	@Override
	public String home() {
		return "dangdang.com";
	}

	@Override
	public String platformName() {
		return "当当网";
	}

	@Override
	public String[] tags() {
		return new String[] {"电商" , "网上书城"};
	}

//	public static void main(String[] args) {
//		System.out.println(new DangDangWangSpider().checkTelephone("18210538513"));
//		System.out.println(new DangDangWangSpider().checkTelephone("18210538577"));
//	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "safe.dangdang.com");
		headers.put("Referer", "http://safe.dangdang.com/find_password.php");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private String getImgCode() {
		Connection.Response response;
		String vcode = null;
		for (int i = 0 ; i < 9; i++) {
			try {
				String imageUrl = "http://vcode.dangdang.com/show_vcode.php?t="+System.currentTimeMillis();
				response = session.connect(imageUrl).headers(getHeader()).execute();
				byte[] body = response.bodyAsBytes();
				vcode = OCRDecode.decodeImageCode(body);
				response = session.connect("http://safe.dangdang.com/p/check_img_vcode.php")
						.method(Method.POST)
						.headers(getHeader())
						.data("txtVcode", vcode)
						.execute();
				if (response.body().contains("1002")) {
					continue;
				}else {
					return vcode;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://safe.dangdang.com/p/check_username_exist.php";
			Connection.Response response = session.connect(url)
					.method(Method.POST)
					.data("txtUser", account)
					.data("txtVcode", getImgCode())
					.headers(getHeader()).ignoreContentType(true).execute();
			String res = response.body();
			if (res.contains("true")) {
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
