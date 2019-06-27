package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;
import okhttp3.Request;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@UsePapaSpider
@Slf4j
public class DuanRongSpider implements PapaSpider {

	@Override
	public String message() {
		return "短融网是创新、透明的互联网借贷信息服务中介平台,深耕“三农”金融,专注于小额资产,为出借人提供省心投、月月盈、散标等出借产品。";
	}

	@Override
	public String platform() {
		return "duanrong";
	}

	@Override
	public String home() {
		return "duanrong.com";
	}

	@Override
	public String platformName() {
		return "短融网";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P" , "借贷"};
	}

//	public static void main(String[] args) {
//		System.out.println(new DuanRongSpider().checkTelephone("18210538513"));
//		System.out.println(new DuanRongSpider().checkTelephone("18210538577"));
//	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
		headers.put("Host", "m.duanrong.com");
		headers.put("Referer", "https://m.duanrong.com/memberLogin");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			Session session = JJsoup.newSession();
			String url = "https://m.duanrong.com/isExist";
			Connection.Response response = session.connect(url)
					.data("mobileNumber", account)
					.method(Method.POST)
					.headers(getHeader())
					.ignoreContentType(true).execute();
			if (response != null) {
				return response.body().contains("true");
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
