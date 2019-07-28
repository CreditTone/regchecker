package com.jisucloud.clawler.regagent.service.impl.health;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class JinXiangDaYaoFangSpider extends PapaSpider {

	@Override
	public String message() {
		return "金象网上药店(金象大药房网上商城)经营产品包括日常药品、母婴用品、两性用品、营养保健、医疗器械、隐形眼镜等。网上购药选择金象网，100%正品保证、方便快捷、安全放心。";
	}

	@Override
	public String platform() {
		return "jinxiang";
	}

	@Override
	public String home() {
		return "jinxiang.com";
	}

	@Override
	public String platformName() {
		return "金象网上药店";
	}

	@Override
	public String[] tags() {
		return new String[] {"健康运动", "医疗", "购药"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15120058878", "13771025665");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://login.jinxiang.com/register/checkUsername.do";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.jianke.com/user/register")
					.post(createUrlEncodedForm("username=" + account))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			return res.contains("110003");
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
