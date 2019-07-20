package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UsePapaSpider
@Slf4j
public class XiaoWeiJinRongSpider extends PapaSpider {

	@Override
	public String message() {
		return "小微金融(weloan.com)是东方银座集团(地产百强)旗下的互联网金融平台,AAA级信用资质、互联网协会会员单位。为个人及中小微企业提供小额贷款、车贷、房贷等出借产品.";
	}

	@Override
	public String platform() {
		return "weloan";
	}

	@Override
	public String home() {
		return "weloan.com";
	}

	@Override
	public String platformName() {
		return "小微金融";
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
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "www.weloan.com");
		headers.put("Referer", "https://www.weloan.com/login?urlSource=https://www.weloan.com/");
		return headers;
	}

	private String getToken(Session session) {
		String home = "https://www.weloan.com/login?urlSource=https://www.weloan.com/";
		Connection.Response response;
		try {
			response = session.connect(home).execute();
			if (response != null) {
				Document doc = Jsoup.parse(response.body());
				Element authenticityToken = doc.select("input[name=authenticityToken]").first();
				if (authenticityToken != null) {
					return authenticityToken.attr("value");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, String> getParams(Session session, String mobile) {
		Map<String, String> params = new HashMap<>();
		params.put("authenticityToken", getToken(session));
		params.put("name", mobile);
		params.put("returnUrl", "");
		params.put("urlSource", "https://www.weloan.com/login?urlSource=https://www.weloan.com/");
		params.put("password", "dwaixopa121129");
		return params;
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.weloan.com/login";
			System.out.println(url);
			Session session = JJsoupUtil.newProxySession();
			Connection.Response response = session.connect(url).method(Method.POST).data(getParams(session, account))
					.headers(getHeader()).ignoreContentType(true).execute();
			if (response != null) {
				return response.body().contains("您的账户信息有误");
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
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
