package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.mockito.internal.util.collections.Sets;

@Slf4j
//@UsePapaSpider
public class PaiPaiDaiSpider implements PapaSpider {
	
	private Session session = JJsoup.newSession();
	
	private String token;

	@Override
	public String message() {
		return "拍拍贷-中国领先互联网金融P2P网贷平台 提供网络贷款，投资理财 小额贷款,短期贷款,个人贷款,无抵押贷款服务 拍拍贷理财借贷投资，获得高年收益率回报，超低门槛，超高收益.";
	}

	@Override
	public String platform() {
		return "ppdai";
	}

	@Override
	public String home() {
		return "ppdai.com";
	}

	@Override
	public String platformName() {
		return "拍拍贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("15900068904", "18210538513");
	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "passport.ppdai.com");
		headers.put("Referer", "https://passport.ppdai.com/resetPassword.html");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private String getImgCode() {
		Connection.Response response;
		String imageCodeUrl = "https://passport.ppdai.com/api/api/changemobile/password_codevalidation";
		for (int i = 0 ; i < 3; i++) {
			try {
				response = session.connect(imageCodeUrl)
						.method(Method.POST)
						.data("undefined", "")
						.data("transId", "")
						.execute();
				JSONObject imageCodeResult = JSON.parseObject(response.body());
				String imageUrl = imageCodeResult.getJSONObject("extrainfo").getJSONObject("imgcode").getString("url");
				token = imageCodeResult.getJSONObject("extrainfo").getJSONObject("imgcode").getString("token");
				response = session.connect(imageUrl).execute();
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
		for (int i = 0 ; i < 3; i++) {
			try {
				String url = "https://passport.ppdai.com/api/api/changemobile/password_step1";
				Connection.Response response = session.connect(url)
						.method(Method.POST)
						.data("emailOrMobile", account)
						.data("code", getImgCode())
						.data("token", token)
						.data("transId", "")
						.headers(getHeader()).ignoreContentType(true).execute();
				JSONObject result = JSON.parseObject(response.body());
				if (result.getString("transid") != null) {
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
