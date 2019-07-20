package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ManPianYiSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "蛮便宜网汇集独家优惠商品全场1折起,天天都有九块九(9.9)包邮商品,秒杀最新9块9包邮独家折扣尽在蛮便宜官网(manpianyi)。";
	}

	@Override
	public String platform() {
		return "manpianyi";
	}

	@Override
	public String home() {
		return "manpianyi.com";
	}

	@Override
	public String platformName() {
		return "蛮便宜网";
	}

	@Override
	public String[] tags() {
		return new String[] {"9.9包邮" , "购物"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18515290717", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://home.manpianyi.com/servet/check_mobile.php";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "home.manpianyi.com")
					.addHeader("Referer", "http://home.manpianyi.com/reg.php")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已注册")) {
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
