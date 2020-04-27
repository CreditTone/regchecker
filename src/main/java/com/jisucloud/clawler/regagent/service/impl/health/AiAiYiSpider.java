package com.jisucloud.clawler.regagent.service.impl.health;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "iiyi.com", 
		message = "爱爱医是面向医务人员的医学、药学专业知识与经验交流平台，并为医生提供国家医学考试中心信息服务的专业医学网站。", 
		platform = "iiyi", 
		platformName = "爱爱医", 
		tags = { "健康运动", "医疗", "生活应用" , "挂号" , "用药" }, 
		testTelephones = { "13877117175", "18212345678" })
public class AiAiYiSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.iiyi.com/index/checkbind";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("bind", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "account.iiyi.com")
					.addHeader("Referer", "https://account.iiyi.com/register?referer=https%3A%2F%2Fwww.iiyi.com%2F")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("已被使用")) {
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
