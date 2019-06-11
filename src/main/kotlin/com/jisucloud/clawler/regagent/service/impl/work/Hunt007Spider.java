package com.jisucloud.clawler.regagent.service.impl.work;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class Hunt007Spider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(20, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	private String email = null;

	@Override
	public String message() {
		return "找工易全国及广东招聘网等各区域人才网站,提供广东人才网求职信息及广州人才网招聘信息,找工作提供专业贴心服务.广东人才招聘网及时发布广东人才市场最新招聘信息。";
	}

	@Override
	public String platform() {
		return "hunt007";
	}

	@Override
	public String home() {
		return "hunt007.com";
	}

	@Override
	public String platformName() {
		return "找工易";
	}

	@Override
	public String[] tags() {
		return new String[] {"求职" , "招聘" , "广东"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new Hunt007Spider().checkTelephone("13691032050"));
//		System.out.println(new Hunt007Spider().checkTelephone("18210538511"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.hunt007.com/Handler/AjaxHandler.ashx?action=GETPWD&u=0&key="+account+"&t=244";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.hunt007.com")
					.addHeader("Referer", "http://www.hunt007.com/GetPwd.aspx?u=0")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			Matcher matcher = Pattern.compile("邮箱: ([^<]+)").matcher(res);
			if (matcher.find()) {
				email = matcher.group(1);
				return true;
			}
			return res.contains("以下是正在使用的账号");
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
		if (email != null) {
			Map<String, String> fields = new HashMap<>();
			fields.put("email", email);
			return fields; 
		}
		return null;
	}

}
