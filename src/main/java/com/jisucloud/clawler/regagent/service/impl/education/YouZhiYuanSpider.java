package com.jisucloud.clawler.regagent.service.impl.education;

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
		home = "youzy.cn", 
		message = "高考志愿填报首选优志愿,最新高考分数线查询,历年全国院校及各专业招生数据查询分析.以大数据提供高考志愿模拟填报系统,根据考分及名次智能推荐高考志愿! 高考志愿热线:400-181-5008。", 
		platform = "youzy", 
		platformName = "优志愿", 
		tags = { "志愿填报", "高考志愿" , "高考查询" }, 
		testTelephones = { "13891156630", "18210538513" })
public class YouZhiYuanSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.youzy.cn/ToCUsers/Users/ValidateMobile";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://www.youzy.cn/accounts/register")
					.post(createJsonForm("{\"mobile\":\""+account+"\",\"authCode\":\"\"}"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string()).getJSONObject("result");
			if (result.getBooleanValue("isExist")) {
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
