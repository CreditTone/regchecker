package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.Session;
import okhttp3.Request;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@UsePapaSpider
@Slf4j
public class MoerlongSpider implements PapaSpider {

	@Override
	public String message() {
		return "摩尔龙致力于为社会普通大众群体提供更加简单、透明及全方位的金融服务。公司业务覆盖全国，每年为大量客户提供包括：信用贷款、抵押贷款、装修贷款、旅游贷款、经营贷款、企业贷款等服务，满足您的全部资金需求。一个电话马上贷款咨询！";
	}

	@Override
	public String platform() {
		return "edai";
	}

	@Override
	public String home() {
		return "edai.com";
	}

	@Override
	public String platformName() {
		return "摩尔龙";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268004", "18210538513");
	}
	
	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "www.edai.com");
		headers.put("Referer", "http://www.edai.com/user/lostPassword/");
		return headers;
	}
	
	private boolean checkImgCode(Session session) {
		String img = "http://www.edai.com/authImg/show/3"+System.currentTimeMillis();
		Connection.Response response;
		for (int i = 0; i < 5; i++) {
			try {
				response = session.connect(img).execute();
				if (response != null) {
					byte[] body = response.bodyAsBytes();
					String imageCode = OCRDecode.decodeImageCode(body);
					String checkUrl = "http://www.edai.com/authimg/verify/?r=0.6649229691142"+new Random().nextInt(1000)+"&authNum="+imageCode;
					Connection.Response checkresponse = session.connect(checkUrl).execute();
					if (checkresponse.body().contains("1")) {
						return true;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			Session session = JJsoupUtil.newProxySession();
			String url = "http://www.edai.com/user/exist/?phone="+account;
			System.out.println(url);
			if (checkImgCode(session)) {
				Connection.Response response = session.connect(url)
						.method(Method.POST)
						.headers(getHeader()).ignoreContentType(true).execute();
				if (response != null) {
					return response.body().contains("1");
				}
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Read timed out")) {
				return false;
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return checkTelephone(account);
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
