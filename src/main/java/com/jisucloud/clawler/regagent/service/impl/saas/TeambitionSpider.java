package com.jisucloud.clawler.regagent.service.impl.saas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "teambition.com", 
		message = "Teambition是国内团队协作工具的创导者，通过帮助团队轻松共享和讨论工作中的任务、文件、分享、日程等内容，让团队协作焕发无限可能。Teambition在网页、桌面、移动环境都打造了体验出众的应用，所以你随时随地都可以和团队协作", 
		platform = "teambition", 
		platformName = "Teambition", 
		tags = { "工作协调" , "沟通平台" }, 
		testTelephones = { "18515290000", "18210538513" })
public class TeambitionSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.teambition.com/api/account/status?phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.teambition.com")
					.addHeader("Referer", "https://account.teambition.com/forgot?")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			JSONObject result = JSON.parseObject(res);
			return result.getBooleanValue("phoneRegistered");
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
