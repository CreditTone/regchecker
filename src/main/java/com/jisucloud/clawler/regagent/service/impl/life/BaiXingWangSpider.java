package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@UsePapaSpider
public class BaiXingWangSpider extends PapaSpider {
	
	private String token;
	private String token2Value;
	private String token2Name;

	@Override
	public String message() {
		return "百姓网，最大的分类信息网。您可以免费查找最新最全的二手物品交易、二手车买卖、房屋租售、宠物、招聘、兼职、求职、交友活动及生活服务等分类信息，还能免费发布这些分类信息。";
	}

	@Override
	public String platform() {
		return "baixing";
	}

	@Override
	public String home() {
		return "baixing.com";
	}

	@Override
	public String platformName() {
		return "百姓网";
	}

	@Override
	public String[] tags() {
		return new String[] {"o2o", "生活休闲", "求职" , "招聘" , "房产家居"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15101030000", "18210538513");
	}

	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "passport.ppdai.com");
		headers.put("Referer", "https://passport.ppdai.com/resetPassword.html");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return Headers.of(headers);
	}
	
	private void getTokenCode() {
		Response response;
		String imageCodeUrl = "http://www.baixing.com/oz/login###";
		try {
			response = get(imageCodeUrl);
			Document doc = Jsoup.parse(response.body().string());
			token = doc.select("input[name=token]").attr("value");
			token2Name = doc.select("input[name=token] ~ input").attr("name");
			token2Value = doc.select("input[name=token] ~ input").attr("value");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			getTokenCode();
			String url = "http://www.baixing.com/oz/login###";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("identity", account)
					.add("token", token)
					.add(token2Name, token2Value)
					.add("password", "woxaomia133")
	                .build();
        	Request request = new Request.Builder().url(url)
        			.headers(getHeader())
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			Document doc = Jsoup.parse(response.body().string());
			if (doc.select(".alert-error").text().contains("密码错误")) {
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
