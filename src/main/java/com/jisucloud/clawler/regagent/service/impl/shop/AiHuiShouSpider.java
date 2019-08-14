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
		home = "aihuishou.com", 
		message = "爱回收国内专业的电子数码产品回收平台,专注数码电子产品回收8年,已拥有上千万用户, 全国上百家门店，面对面回收，当场付款，同时还支持一键上门回收,免费快递回收等多种回收方式,为用户提供更方便，更正规的回收方式，爱回收变废为宝,让闲置不用都物尽其用。", 
		platform = "aihuishou", 
		platformName = "爱回收", 
		tags = { "二手购物" , "3C产品" }, 
		testTelephones = { "13800000000", "18210538513" })
public class AiHuiShouSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.aihuishou.com/portal-user/account/loginbypwd";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("pwd", "c229482cb")
	                .add("remenberUser", "true")
	                .add("loading", "true")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.aihuishou.com")
					.addHeader("Referer", "https://www.aihuishou.com/pc/index.html#/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码错误")) {
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
