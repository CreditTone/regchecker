package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep007.spiderbase.okhttp.OKHttpUtil;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@UsePapaSpider
public class SuPeiWangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();

	@Override
	public String message() {
		return "速配网是面向全球华人的自助式征婚网站,免费注册,我们为真诚会员提供更多免费婚恋服务! - 速配网专注于网上征婚,不是婚介,不做交友,不良交友目的者请勿进入。";
	}

	@Override
	public String platform() {
		return "supei";
	}

	@Override
	public String home() {
		return "supei.com";
	}

	@Override
	public String platformName() {
		return "速配网";
	}

	@Override
	public String[] tags() {
		return new String[] {"单身交友", "社区"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18515290717", "18210538513");
	}
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(SuPeiWangSpider.class);
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.supei.com/www/login.jsp";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("loginid", account)
	                .add("password", "Useas121rName")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "www.supei.com")
					.addHeader("Referer", "http://www.supei.com/www/loginpage.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			Document doc = Jsoup.parse(response.body().string());
			if (doc.select("font[color=red]").text().contains("密码")) {
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
