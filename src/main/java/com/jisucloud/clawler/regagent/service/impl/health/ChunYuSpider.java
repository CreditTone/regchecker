package com.jisucloud.clawler.regagent.service.impl.health;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "chunyuyisheng.com", 
		message = "春雨医生提供真实医生的在线医疗健康咨询服务。由公立医院医师解答用户的健康问题。", 
		platform = "chunyuyisheng", 
		platformName = "春雨医生", 
		tags = { "健康运动", "医疗", "生活应用" , "挂号" , "用药" }, 
		testTelephones = { "13910252045", "18210538513" })
public class ChunYuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://api.chunyuyisheng.com/api/accounts/is_registered/?app=0&client=me.chunyu.ChunyuDoctor&platform=android&version=8.6.0&app_ver=8.6.0&imei=352284040670808&device_id=352284040670808&phoneType=8692-A00_by_QiKU&vendor=anzhi";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Chunyuyisheng/8.6.0 (Android 4.4.2;8692-A00_by_QiKU)")
					.addHeader("Host", "api.chunyuyisheng.com")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("is_registered\": true") || res.contains("is_registered\":true")) {
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
