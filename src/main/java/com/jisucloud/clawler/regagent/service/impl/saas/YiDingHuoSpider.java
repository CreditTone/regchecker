package com.jisucloud.clawler.regagent.service.impl.saas;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "dinghuo123.com", 
		message = "易订货帮助企业快速构建全渠道营销互动平台，已连续三年蝉联最佳企业移动订货平台大奖。围绕品牌企业与下游客户的全渠道业务流程设计，以订单处理为核心，实现在商机管理、分销管控、商品促销、订单。", 
		platform = "dinghuo123", 
		platformName = "易订货", 
		tags = { "b2b" ,"商机" ,"生意" }, 
		testTelephones = { "15120058878", "18212345678" },
		exclude = true)
public class YiDingHuoSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://sso.dinghuo123.com/username/exist";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userName", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://sso.dinghuo123.com/apply/apply2")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("用户名重复")) {
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
