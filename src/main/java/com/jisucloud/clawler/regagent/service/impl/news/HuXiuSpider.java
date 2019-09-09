package com.jisucloud.clawler.regagent.service.impl.news;


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
		home = "huxiu.com", 
		message = "创办于2012年5月，虎嗅网是一个聚合优质创新信息与人群的新媒体平台。这个平台专注于贡献原创、深度、犀利优质的商业资讯、围绕创新创业的观点剖析与交流。", 
		platform = "huxiu", 
		platformName = "虎嗅网", 
		tags = { "新闻资讯" }, 
		testTelephones = { "18720982607", "18210538513" })
public class HuXiuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.huxiu.com/user_action/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("password", "adasd1231")
	                .add("country", "+86")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", IOS_USER_AGENT)
					.addHeader("Host", "m.huxiu.com")
					.addHeader("Referer", "https://m.huxiu.com/user/login_sms")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("密码")) {
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
