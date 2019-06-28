package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class DabaiqicheSpider implements PapaSpider {

	@Override
	public String message() {
		return "“大白汽车分期”是为购车人群提供分期服务的新车购车APP.";
	}

	@Override
	public String platform() {
		return "qufenqi";
	}

	@Override
	public String home() {
		return "qufenqi.com";
	}

	@Override
	public String platformName() {
		return "大白汽车分期";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538577", "18210538513");
	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		return headers;
	}
	
	private Map<String, String> getParams(String mobile,String code) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("imgcode", code);
        return params;
    }
	
	private String getImgCode(Session session) {
		String img = "https://passport.qufenqi.com/verify/getimg?r=0."+System.currentTimeMillis();
		Connection.Response response;
		try {
			response = session.connect(img).execute();
			if (response != null) {
				byte[] body = response.bodyAsBytes();
				return OCRDecode.decodeImageCode(body);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean checkTelephone(String account) {
		Session session = JJsoup.newSession();
		for (int i = 0; i < 5; i++) {//最大尝试5次
			try {
				String url = "https://passport.qufenqi.com/i/resetloginpass/setaccount";
				String imgcode = getImgCode(session);
				Connection.Response response = session.connect(url)
						.method(Method.POST)
						.data(getParams(account, imgcode))
						.headers(getHeader()).ignoreContentType(true).execute();
				if (response != null ) {
					JSONObject result = JSON.parseObject(response.body());
					log.info(result.toJSONString());
					int code = result.getIntValue("code");
					if (code == 1) {//未注册
						return false;
					}
					if (code == 0) {//已注册
						return true;
					}
					//验证码错误
					continue;
				}
			} catch (Exception e) {
				if (e.getMessage().contains("Read timed out")) {
					return false;
				}
				e.printStackTrace();
			}
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
