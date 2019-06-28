package com.jisucloud.clawler.regagent.service.impl.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class LiePinWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "猎聘网，知名中高端人才招聘网站，汇聚行业名企。为求职者提供免费注册，热门职位推送，猎头精准推荐，24小时急速反馈等服务。每日新增数万条名企招聘信息，猎头在线服务，真实职位信息。官方首页www.liepin.com，8秒注册填简历，找工作更快，更轻松！";
	}

	@Override
	public String platform() {
		return "liepin";
	}

	@Override
	public String home() {
		return "liepin.com";
	}

	@Override
	public String platformName() {
		return "猎聘网";
	}

	@Override
	public String[] tags() {
		return new String[] {"求职", "招聘"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13210038513", "18210538513");
	}
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(LiePinWangSpider.class);
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.liepin.com/forgetpwd/v1/checklogin.json?_=" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("login", account)
	                .add("clientId", "")
	                .add("userCategory", "1")
	                .add("backurl", "")
	                .add("terminal", "2")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.liepin.com")
					.addHeader("Referer", "https://passport.liepin.com/forgetpwd/m/v1/index/")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			JSONObject result = JSON.parseObject(res);
			if (result.getString("flag").equals("1")) {
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
