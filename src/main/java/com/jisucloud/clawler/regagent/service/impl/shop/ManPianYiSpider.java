package com.jisucloud.clawler.regagent.service.impl.shop;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "manpianyi.com", 
		message = "蛮便宜网汇集独家优惠商品全场1折起,天天都有九块九(9.9)包邮商品,秒杀最新9块9包邮独家折扣尽在蛮便宜官网(manpianyi)。", 
		platform = "manpianyi", 
		platformName = "蛮便宜网", 
		tags = { "9.9包邮" , "购物" }, 
		testTelephones = { "18515290717", "18210538513" })
public class ManPianYiSpider extends PapaSpider {

	

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
