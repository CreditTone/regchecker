package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class JinTouHangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "金投行4周年 让钱变得有温度 关注微信服务号 资金变动尽在掌握 智选专区 智能出借◎一键实现安全有保障 国有全资,3亿资本实力, 财险公司提供保证保险。";
	}

	@Override
	public String platform() {
		return "jintouxing";
	}

	@Override
	public String home() {
		return "jintouxing.com";
	}

	@Override
	public String platformName() {
		return "金投行";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.jintouxing.com/register/checkUserName.htm";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("action", "mobileCheck")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.jintouxing.com")
					.addHeader("Referer", "https://www.jintouxing.com/register/registerIndex.htm")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被注册")) {
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
