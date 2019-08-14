package com.jisucloud.clawler.regagent.service.impl.social;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@PapaSpiderConfig(
		home = "renren.com", 
		message = "renren", 
		platform = "renren", 
		platformName = "人人网", 
		tags = { "论坛" , "社交" , "校园" }, 
		testTelephones = { "18810038000", "18210538513" })
public class RenRenSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			FormBody formBody = new FormBody
	                .Builder()
	                .add("authType", "email")
	                .add("stage", "3")
	                .add("t", "" + System.currentTimeMillis())
	                .add("value", account)
	                .add("requestToken", "")
	                .add("_rtk", "b08da1d7")
	                .build();
			Request request = new Request.Builder().url("http://reg.renren.com/AjaxRegisterAuth.do")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "reg.renren.com")
					.addHeader("Referer", "http://reg.renren.com/xn6218.do?ss=10131&rt=1&f=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DXQV23PAco2JzFTBKTWsnT202u0cwTwpHIk-L5mpGsXO%26wd%3D%26eqid%3Db977c5e000031d7e000000025cff7b6d&g=v6reg")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已经绑定")) {
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
