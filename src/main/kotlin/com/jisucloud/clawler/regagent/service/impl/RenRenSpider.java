package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RenRenSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "renren";
	}

	@Override
	public String platform() {
		return "renren";
	}

	@Override
	public String home() {
		return "renren.com";
	}

	@Override
	public String platformName() {
		return "CSDN";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("社交", new String[] { "校园" });
			}
		};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new RenRenSpider().checkTelephone("13879691485"));
		Thread.sleep(3000);
		System.out.println(new RenRenSpider().checkTelephone("18210538513"));
	}
	
	private String getImgCode(String _captcha_type) {
		String img = "http://icode.renren.com/getcode.do?proxy_host=icode.renren.com&proxy_uri=getcode.do&rk=800&t="+_captcha_type+"&rnd=0.6917277028166485";
		try {
			Response response = okHttpClient.newCall(new Request.Builder().url(img).build()).execute();
			if (response != null) {
				byte[] body = response.body().bytes();
				return OCRDecode.decodeImageCode(body);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "c2a1";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			Request request = new Request.Builder().url("https://safe.renren.com/standalone/findpwd#nogo")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "safe.renren.com")
					.addHeader("Referer", "https://safe.renren.com")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			Document doc = Jsoup.parse(response.body().string());
			String action_token = doc.select("input[name=action_token]").first().attr("value");
			String _captcha_type = doc.select("input[name=_captcha_type]").first().attr("value");
			String domain = "renren.com";
			String _rtk = null;
			String token = null;
			Matcher matcher = Pattern.compile("page_token = \"([^\"]+)").matcher(doc.html());
			if (matcher.find()) {
				token = matcher.group(1);
			}
			matcher = Pattern.compile("get_check_x:'([^']+)").matcher(doc.html());
			if (matcher.find()) {
				_rtk = matcher.group(1);
			}
			String url = "https://safe.renren.com/standalone/findpwd/inputaccount";
			if (token != null && _rtk != null) {
				for (int i = 0 ; i < 10 ; i++) {//验证码解析重试
					FormBody formBody = new FormBody
			                .Builder()
			                .add("action_token",action_token)
			                .add("_captcha_type",_captcha_type)
			                .add("domain",domain)
			                .add("account",account)
			                .add("token", token)
			                .add("_rtk", _rtk)
			                .add("captcha",getImgCode(_captcha_type))
			                .add("ajax-type","json")
			                .build();
					request = new Request.Builder().url(url)
							.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
							.addHeader("Host", "safe.renren.com")
							.addHeader("Referer", "https://safe.renren.com")
							.post(formBody)
							.build();
					response = okHttpClient.newCall(request).execute();
					if (response != null) {
						String res = response.body().string();
						if (res.contains("验证码不正确")) {
							log.warn("验证码不正确:"+formBody);
							continue;
						}
						JSONObject result = JSON.parseObject(res);
						log.info("csdn:" + result);
						if (result.getBooleanValue("status")) {
							return true;
						}else {
							return false;
						}
					}
				}
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
