package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class TouYouQuanSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "投友圈是具有社交、交易功能的金融社区，于2014年10月15日正式上线，注册用户已超过10000人。投友圈是由从业于金融、互联网行业多年的精英团队创建，团队成员来自于百度、奇虎360等知名的互联网公司。投友圈本着分散投资，减小风险的理念。";
	}

	@Override
	public String platform() {
		return "touyouquan";
	}

	@Override
	public String home() {
		return "touyouquan.com";
	}

	@Override
	public String platformName() {
		return "投友圈";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "社交" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.touyouquan.com/jsp/user/userLogin.htm";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userName", account)
	                .add("passwd", "moaasd12bia12le")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.touyouquan.com")
					.addHeader("Referer", "https://www.touyouquan.com/html/login.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("loginError")) {
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
