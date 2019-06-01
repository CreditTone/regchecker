package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class MangGuoTVSpider implements PapaSpider {
	
	private Session session = JJsoup.newSession();
	
	@Override
	public String message() {
		return "芒果TV是湖南广播电视台旗下唯一互联网视频平台，独家提供湖南卫视所有栏目高清视频直播点播，并为用户提供各类热门电影、电视剧、综艺、动漫、音乐、娱乐等内容。";
	}

	@Override
	public String platform() {
		return "mgtv";
	}

	@Override
	public String home() {
		return "mgtv.com";
	}

	@Override
	public String platformName() {
		return "芒果TV";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("娱乐", new String[] {});
			}
		};
	}

//	public static void main(String[] args) {
//		System.out.println(new MangGuoTVSpider().checkTelephone("18210538513"));
//		System.out.println(new MangGuoTVSpider().checkTelephone("18210538577"));
//	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "i.mgtv.com");
		headers.put("Referer", "https://www.mgtv.com/");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private String getImgCode() {
		Connection.Response response;
		for (int i = 0 ; i < 3; i++) {
			try {
				String imageUrl = "https://i.mgtv.com/vcode?from=pcclient&time="+System.currentTimeMillis();
				response = session.connect(imageUrl).headers(getHeader()).execute();
				if (response != null) {
					byte[] body = response.bodyAsBytes();
					return OCRDecode.decodeImageCode(body);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	@Override
	public boolean checkTelephone(String account) {
		session.connect("https://www.mgtv.com/");
		for (int i = 0 ; i < 6; i++) {
			try {
				String url = "https://i.mgtv.com/account/loginVerify";
				Connection.Response response = session.connect(url)
						.method(Method.POST)
						.data("account", account)
						.data("sub", "1")
						.data("vcode", getImgCode())
						.data("pwd", "13734bd8b64952f8732b11653fbb5a5e8e777a19306593078f3d071df2cd5fe9b7beaf51e02372e37ad87a5caf63d009d587dfc32fafcdada52d067153317c6453e937aafeba7b0b20d0c3eb2a73f92d04a838507dcb688fa9bd779ae6564d6187a33de2518aaef9903e014a104447f0bd712f6ce35fabe8919969467aeb7ab7")
						.data("remember", "1")
						.headers(getHeader()).ignoreContentType(true).execute();
				String res = response.body();
				if (res.contains("code\":-125")) {//验证码错误
					continue;
				}
				if (res.contains("\\u5e10\\u53f7\\u6216\\u5bc6\\u7801\\u8f93")) {
					return true;
				}else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
