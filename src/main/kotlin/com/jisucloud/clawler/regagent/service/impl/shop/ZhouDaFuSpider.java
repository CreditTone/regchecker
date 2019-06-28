package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

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
public class ZhouDaFuSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "周大福－亚洲最值得信赖的钻石、黄金珠宝品牌，为您提供最优质的在线购物服务，数千款钻石、钻戒、戒指、吊坠、项链、黄金饰品供您选购!";
	}

	@Override
	public String platform() {
		return "ctfmall";
	}

	@Override
	public String home() {
		return "ctfmall.com";
	}

	@Override
	public String platformName() {
		return "周大福";
	}

	@Override
	public String[] tags() {
		return new String[] {"电商" , "首饰"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18779861101", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.ctfmall.com/ajax.ashx?action=UserLogin&t=0.37937339"+System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("password", "c229482cbf60ea98e9d84165d07365b8")
	                .add("authCode", "")
	                .add("smsCode", "")
	                .add("keep", "1")
	                .add("type", "0")
	                .add("needVerify", "0")
	                .add("refurl", "/user/user_center.aspx")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "m.ctfmall.com")
					.addHeader("Referer", "https://m.ctfmall.com/user/login.aspx?refurl=/user/user_center.aspx")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("请输入正确的密码")) {
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
