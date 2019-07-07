package com.jisucloud.clawler.regagent.service.impl.health;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class AiAiYiSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "爱爱医是面向医务人员的医学、药学专业知识与经验交流平台，并为医生提供国家医学考试中心信息服务的专业医学网站。";
	}

	@Override
	public String platform() {
		return "iiyi";
	}

	@Override
	public String home() {
		return "iiyi.com";
	}

	@Override
	public String platformName() {
		return "爱爱医";
	}

	@Override
	public String[] tags() {
		return new String[] {"健康运动", "医疗", "生活应用" , "挂号" , "用药"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13877117175", "18210538513");
	}

	@Override
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
