package com.jisucloud.clawler.regagent.service.impl.car;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class AoTuZuCheSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "凹凸租车(atzuche)成立于2013年7月1日，总部设在上海，通过凹凸平台，车主将自己闲置车辆租给他人使用，租客使用车况更好的车辆。凹凸租车于2014年9月正式更名为凹凸共享租车。";
	}

	@Override
	public String platform() {
		return "atzuche";
	}

	@Override
	public String home() {
		return "atzuche.com";
	}

	@Override
	public String platformName() {
		return "凹凸租车";
	}

	@Override
	public String[] tags() {
		return new String[] {"租车"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538577", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.atzuche.com/appserver/h5/v31/mem/action/exists";
			RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"), "{\"mobile\":\"" + account + "\",\"requestId\":" + System.currentTimeMillis() + "}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "m.atzuche.com")
					.addHeader("Referer", "https://m.atzuche.com/m/login/?redirect=https%3A%2F%2Fm.atzuche.com%2Fm%2FuserCenter%2F&isBind=1")
					.addHeader("Accept", "application/json;version=3.0;compress=false")
					.post(requestBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getJSONObject("data").getString("isNew").equals("0")) {
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
