package com.jisucloud.clawler.regagent.service.impl.reader;

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
		home = "sxyj.net", 
		message = "书香云集小说网提供最好看的原创免费小说在线阅读,坚持做更新快,原创度高的小说阅读网，致力做中国最好的原创小说。", 
		platform = "sxyj", 
		platformName = "书香云集", 
		tags = { "电子书", "阅读" , "小说" }, 
		testTelephones = { "18685656206", "18210538513" })
public class ShuXiangYunJiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.sxyj.net/WebApi/Account/ExistPhone?phone="+account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return result.getBooleanValue("data");
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
