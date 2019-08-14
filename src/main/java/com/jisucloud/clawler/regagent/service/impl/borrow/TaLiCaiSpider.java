package com.jisucloud.clawler.regagent.service.impl.borrow;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@PapaSpiderConfig(
		home = "talicai.com", 
		message = "她理财网（www.talicai.com）是国内首家女性理财网站。让财女们在这里学习家庭理财、生活理财。以及女性理财投资规划、理财经验事实分享。记录理财生活点滴，分享理财方法，一起成长为经济独立和财务。", 
		platform = "talicai", 
		platformName = "她理财网", 
		tags = { "女性", "理财" }, 
		testTelephones = { "18210538513", "15161509916" })
public class TaLiCaiSpider extends PapaSpider {
	
	private String getCsrfToken() throws Exception {
		String url = "https://www.talicai.com/accounts/password/forgot";
		Response response = okHttpClient.newCall(createRequest(url)).execute();
		Document doc = Jsoup.parse(response.body().string());
		return doc.select("input[name=_csrf_token]").attr("value");
	}

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

}
