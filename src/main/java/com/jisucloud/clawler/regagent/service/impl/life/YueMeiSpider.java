package com.jisucloud.clawler.regagent.service.impl.life;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "yuemei.com", 
		message = "悦美网是专业的整形美容平台,提供全新的美容整形资讯、优惠的整形美容项目,汇集齐全的整形美容医院、医生,悦美网为医疗美容爱好者全心打造中立权威的医疗美容网站。", 
		platform = "yuemei", 
		platformName = "悦美网", 
		tags = { "医美", "美容" , "整容" }, 
		testTelephones = { "18515290717", "18212345678" })
public class YueMeiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://user.yuemei.com/user/ajaxIsPhoneExist";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "user.yuemei.com")
					.addHeader("Referer", "https://user.yuemei.com/user/register/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("0")) {
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
