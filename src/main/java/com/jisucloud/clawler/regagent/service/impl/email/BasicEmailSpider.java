package com.jisucloud.clawler.regagent.service.impl.email;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.i.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class BasicEmailSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(false).build();

	@Override
	public String[] tags() {
		return new String[] { "邮箱" };
	}

	public abstract String getEmail(String account);

	@Override
	public boolean checkTelephone(String account) {
		String email = getEmail(account);
		if (email == null) {
			return false;
		}
		try {
			String url = "http://www.emailcamel.com/api/single/validate/?usr=guozhong@quicklyun.com&pwd=qqadmin127&email="
					+ email;
			Request request = new Request.Builder().url(url).build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string()).getJSONObject("result");
			if ("success".equals(result.getString("verify_status"))) {
				if ("valid".equals(result.getString("verify_result"))) {
					return true;
				}
				if ("catch-all".equals(result.getString("verify_result"))) {
					return true;
				}
			} else {
				log.error("emailcamel效验失败，请充值");
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
