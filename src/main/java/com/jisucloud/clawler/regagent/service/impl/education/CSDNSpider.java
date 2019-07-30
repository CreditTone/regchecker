package com.jisucloud.clawler.regagent.service.impl.education;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class CSDNSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "中国专业IT社区CSDN (Chinese Software Developer Network) 创立于1999年，致力于为中国软件开发者提供知识传播、在线学习、职业发展等全生命周期服务。";
	}

	@Override
	public String platform() {
		return "csdn";
	}

	@Override
	public String home() {
		return "csdn.net";
	}

	@Override
	public String platformName() {
		return "CSDN";
	}

	@Override
	public String[] tags() {
		return new String[] {"新闻阅读","it资料"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18720982607", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.csdn.net/v1/service/mobiles/"+account+"?comeFrom=0&code=0086";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.csdn.net")
					.addHeader("Referer", "https://passport.csdn.net/forget")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response != null) {
				JSONObject result = JSON.parseObject(response.body().string());
				if (result.getBooleanValue("status")) {
					return true;
				}
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
