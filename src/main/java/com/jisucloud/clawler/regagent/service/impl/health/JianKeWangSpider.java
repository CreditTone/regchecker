package com.jisucloud.clawler.regagent.service.impl.health;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

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
public class JianKeWangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "健客网,做中国最大网上药店,经国家药监局认证的合法网上药店和正规药房网,买药省30%,100%正品.提供专业、优质和便捷的网上购药服务,执业医师为您提供24小时健康咨询!网上药店哪个好?首选健客网。";
	}

	@Override
	public String platform() {
		return "jianke";
	}

	@Override
	public String home() {
		return "jianke.com";
	}

	@Override
	public String platformName() {
		return "健客网";
	}

	@Override
	public String[] tags() {
		return new String[] {"健康运动", "医疗", "购药"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13877117170", "13771025665");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://wcgi.jianke.com/v2/member/account/validate";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"), "{\"userName\":\""+account+"\"}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.jianke.com/user/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			JSONObject result = JSON.parseObject(res);
			return result.getString("isRegisted").equals("1");
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
