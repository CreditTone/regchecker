package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@UsePapaSpider
public class TaLiCaiSpider extends PapaSpider {

	@Override
	public String message() {
		return "她理财网（www.talicai.com）是国内首家女性理财网站。让财女们在这里学习家庭理财、生活理财。以及女性理财投资规划、理财经验事实分享。记录理财生活点滴，分享理财方法，一起成长为经济独立和财务。";
	}

	@Override
	public String platform() {
		return "talicai";
	}

	@Override
	public String home() {
		return "talicai.com";
	}

	@Override
	public String platformName() {
		return "她理财网";
	}

	@Override
	public String[] tags() {
		return new String[] {"女性", "理财"};
	}
	
	private String getCsrfToken() throws Exception {
		String url = "https://www.talicai.com/accounts/password/forgot";
		Response response = okHttpClient.newCall(createRequest(url)).execute();
		Document doc = Jsoup.parse(response.body().string());
		return doc.select("input[name=_csrf_token]").attr("value");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.talicai.com/accounts/password/forgot";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("alias", account)
	                .add("_csrf_token", getCsrfToken())
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.talicai.com/accounts/password/forgot")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码长度必须在8-16位，且同时包含数字和字母")) {
				return true;
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

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15161509916");
	}

}
