package com.jisucloud.clawler.regagent.service.impl.knowledge;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "hdb.com", 
		message = "互动吧是活动平台，通过三年多的发展，已拥有7000多万注册用户。互动吧是一座连接活动主办方与参与者的桥梁，一方面帮助主办方更简单、高效的创建活动、管理活动和传播活动，另一方面帮助参与者更轻松、便捷的找到喜欢的活动参加。", 
		platform = "hdb", 
		platformName = "互动吧", 
		tags = { "论坛" , "交友" , "户外交友" }, 
		testTelephones = { "13925306966", "18212345678" })
public class HuDongBaSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.hdb.com/post/api:2";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("login_name", account)
	                .add("login_password", "xoan2ncoam3")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.hdb.com")
					.addHeader("Referer", "https://www.hdb.com/login/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			return res.contains("密码输入错误");
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
