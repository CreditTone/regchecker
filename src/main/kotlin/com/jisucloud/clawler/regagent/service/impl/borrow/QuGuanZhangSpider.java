package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class QuGuanZhangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "趣管账是一款专为工薪阶层打造，用手机借贷提供分期消费、小额借款的移动互联网信贷产品，其宗旨是为20-45周岁的人群提供便捷迅速的金融信贷服务。国内首批利用大数据，人工智能实现风控审核的信贷服务平台。";
	}

	@Override
	public String platform() {
		return "finsphere";
	}

	@Override
	public String home() {
		return "finsphere.cn";
	}

	@Override
	public String platformName() {
		return "趣管账";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P" , "小额借款"};
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18810038000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.finsphere.cn/abook/data/ws/rest/user/login";
			String postdata = "{\n" + 
					"                            \"mobileNo\": \""+account+"\",\n" + 
					"                            \"deviceId\": \"163b9a2f-fe31-3641-8e6b-dcb95069849c\",\n" + 
					"                            \"password\": \"670B14728AD9902AECBA32E22FA4F6BD\",\n" + 
					"                            \"imei\": \"AC4CC966B434697952CEC6ED0C28EC66\",\n" + 
					"                            \"needToken\": false,\n" + 
					"                            \"appVerison\": \"3.3.0\",\n" + 
					"                            \"channel\": \"A0004\",\n" + 
					"                            \"loginKind\": \"normal\"\n" + 
					"                        }";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", ANDROID_USER_AGENT)
					.addHeader("Host", "www.finsphere.cn")
					.post(FormBody.create(MediaType.get("application/json; charset=utf-8"), postdata))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String errorMsg = response.body().string();
			if (errorMsg.contains("密码错误")) {
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
