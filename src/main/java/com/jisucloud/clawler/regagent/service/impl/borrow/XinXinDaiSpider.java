package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class XinXinDaiSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "新新贷(xinxindai.com) 总部位于上海市虹口区，专注于中小微用户金融信息服务, 资金银行存管。 中国互联网金融协会会员单位，截止2019年3月底，平台已累计交易总额突破140亿+。";
	}

	@Override
	public String platform() {
		return "xinxindai";
	}

	@Override
	public String home() {
		return "xinxindai.com";
	}

	@Override
	public String platformName() {
		return "新新贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "小微金融" , "借贷"};
	}
	
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.xinxindai.com/user/checkMobile.html";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.xinxindai.com")
					.addHeader("Referer", "https://www.xinxindai.com/user/iregister.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
