package com.jisucloud.clawler.regagent.service.impl.trip;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class BuDingJiuDianSpider extends PapaSpider {


	@Override
	public String message() {
		return "布丁酒店连锁是全国快捷连锁酒店前10强,为客户提供经济型客房,支持在线酒店预订,电话预订,wap预订,手机客户端预订,手机版预订。";
	}

	@Override
	public String platform() {
		return "podinns";
	}

	@Override
	public String home() {
		return "podinns.com";
	}

	@Override
	public String platformName() {
		return "布丁酒店";
	}

	@Override
	public String[] tags() {
		return new String[] {"旅行", "酒店"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13810973590", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://www.podinns.com/Account/MobileExist?value=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.9588.com/Home/Register")
					.post(createUrlEncodedForm(""))
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().toUpperCase().contains("FALSE");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
