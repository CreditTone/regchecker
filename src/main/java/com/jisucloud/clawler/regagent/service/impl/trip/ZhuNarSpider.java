package com.jisucloud.clawler.regagent.service.impl.trip;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "zhuna.com", 
		message = "住哪网是中国最专业的在线旅行住宿服务平台,提供国内40000多家酒店、全球260000家海外酒店的预订服务,及短租公寓、民宿客栈等预订服务。订酒店,返现金!", 
		platform = "zhuna", 
		platformName = "住哪网", 
		tags = { "旅游" , "酒店" }, 
		testTelephones = { "13910252045", "18210538513" })
public class ZhuNarSpider extends PapaSpider {

	private String name = null;

	public boolean checkTelephone(String account) {
		try {
			name = null;
			String url = "http://www.zhuna.cn/account/isreg/?mobile="+ account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.zhuna.cn")
					.addHeader("Referer", "http://www.zhuna.cn/account/register/")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			Object data = result.get("data");
			if (data instanceof JSONArray) {
				JSONArray data2 = (JSONArray) data;
				JSONObject item = data2.getJSONObject(0);
				name = item.getString("NickName");
				name = StringUtil.unicodeToString(name);
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
		if (name != null) {
			Map<String, String> fields = new HashMap<>();
			fields.put("name" , name);
			return fields;
		}
		return null;
	}

}
