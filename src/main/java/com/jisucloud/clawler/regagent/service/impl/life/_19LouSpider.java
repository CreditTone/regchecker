package com.jisucloud.clawler.regagent.service.impl.life;

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
public class _19LouSpider extends PapaSpider {


	@Override
	public String message() {
		return "19楼（www.19lou.com），中国最大的本地生活交流与服务平台，致力于为各地用户提供便捷的生活交流空间和体贴的本地生活服务。";
	}

	@Override
	public String platform() {
		return "19lou";
	}

	@Override
	public String home() {
		return "19lou.com";
	}

	@Override
	public String platformName() {
		return "19楼";
	}

	@Override
	public String[] tags() {
		return new String[] {"社区", "生活服务"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18523857478", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "https://www.19lou.com/register/checkmobile";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://www.19lou.com/register")
					.post(createUrlEncodedForm("mobile="+account))
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return !result.getBooleanValue("success");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
