package com.jisucloud.clawler.regagent.service.impl.social;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@UsePapaSpider
public class RenRenSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "renren";
	}

	@Override
	public String platform() {
		return "renren";
	}

	@Override
	public String home() {
		return "renren.com";
	}

	@Override
	public String platformName() {
		return "人人网";
	}

	@Override
	public String[] tags() {
		return new String[] {"论坛" , "社交" , "校园"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18810038000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			FormBody formBody = new FormBody
	                .Builder()
	                .add("authType", "email")
	                .add("stage", "3")
	                .add("t", "" + System.currentTimeMillis())
	                .add("value", account)
	                .add("requestToken", "")
	                .add("_rtk", "b08da1d7")
	                .build();
			Request request = new Request.Builder().url("http://reg.renren.com/AjaxRegisterAuth.do")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "reg.renren.com")
					.addHeader("Referer", "http://reg.renren.com/xn6218.do?ss=10131&rt=1&f=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DXQV23PAco2JzFTBKTWsnT202u0cwTwpHIk-L5mpGsXO%26wd%3D%26eqid%3Db977c5e000031d7e000000025cff7b6d&g=v6reg")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已经绑定")) {
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
