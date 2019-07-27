package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

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

	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "www.weloan.com");
		headers.put("Referer", "https://www.weloan.com/login?urlSource=https://www.weloan.com/");
		return Headers.of(headers);
	}

	private String getToken() {
		String home = "https://www.weloan.com/login?urlSource=https://www.weloan.com/";
		Response response;
		try {
			response = get(home);
			if (response != null) {
				Document doc = Jsoup.parse(response.body().string());
				Element authenticityToken = doc.select("input[name=authenticityToken]").first();
				if (authenticityToken != null) {
					return authenticityToken.attr("value");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private FormBody getParams(String mobile) {
		FormBody formBody = new FormBody
                .Builder()
                .add("authenticityToken", getToken())
        		.add("name", mobile)
        		.add("returnUrl", "")
        		.add("urlSource", "https://www.weloan.com/login?urlSource=https://www.weloan.com/")
        		.add("password", "dwaixopa121129")
                .build();
		return formBody;
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.weloan.com/login";
			Request request = new Request.Builder().url(url)
					.headers(getHeader())
					.post(getParams(account))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("您的账户信息有误");
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
