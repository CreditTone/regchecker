package com.jisucloud.clawler.regagent.service.impl.health;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "ikang.com", 
		message = "爱康国宾体检中心是全国知名体检中心,是拥有多年健康体检及健康管理经验的专业综合性健康管理机构;提供健康体检(男体检/女性体检)、健康检查。", 
		platform = "ikang", 
		platformName = "国宾体检", 
		tags = { "健康运动", "医疗", "体检" }, 
		testTelephones = { "15901537458", "18210538513" })
public class IKangGuoBinSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://oauth2.health.ikang.com/register/request";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Chunyuyisheng/8.6.0 (Android 4.4.2;8692-A00_by_QiKU)")
					.addHeader("Host", "oauth2.health.ikang.com")
					.addHeader("Referer", "https://oauth2.health.ikang.com/register1?response_type=token&client_id=6237cf0f-b603-4246-8f81-88a64b0c889b&state=xyz&channel_id=h5&channel_name=h5_mobile&type_3rd=alipay&redirect_uri=http://m.ikang.com/auth.html&m=1")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已经被使用")) {
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
