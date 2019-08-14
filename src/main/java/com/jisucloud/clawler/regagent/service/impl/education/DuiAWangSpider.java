package com.jisucloud.clawler.regagent.service.impl.education;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "duia.com", 
		message = "对啊网|中国领先的移动互联网职业教育企业,提供职业技能类,学历类免费课程课程,在线系统班,在线题库,答疑社区,致力于帮助每一位在职人群重塑职业未来。", 
		platform = "duia", 
		platformName = "对啊网", 
		tags = { "考试","学习","教育" }, 
		testTelephones = { "15584382173", "18210538513" })
public class DuiAWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://sso.duia.com/register/validate-mobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "sso.duia.com")
					.addHeader("Referer", "https://sso.duia.com/g-p/wap/register?returnUrl=https%3A%2F%2Fmuc.duia.com%2Fuser")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被注册")) {
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
