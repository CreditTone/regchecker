package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "dhibank.com", 
		message = "德鸿普惠综合金融服务商,是通过“互联网+金融”战略计划,依托移动互联网大数据为基础的,为微小企业和个人提供小额的金融借贷服务普惠金融的平台。", 
		platform = "dhibank", 
		platformName = "德鸿普惠", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class DeHongPuHuiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.dhibank.net/rest/api/app/user/appLogin";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json;charset=UTF-8"), "{\"customerType\":1001,\"registerType\":1017,\"password\":\"89b9168e84bad294037805f8014d5384\",\"username\":\""+account+"\",\"client\":2}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", ANDROID_USER_AGENT)
					.addHeader("Host", "m.dhibank.net")
					.addHeader("CustomHead", "{\"client\":2,\"customerType\":\"1001\",\"registerType\":\"1017\"}")
					.addHeader("Referer", "https://m.dhibank.net/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("密码错误") || res.contains("锁定")) {
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
