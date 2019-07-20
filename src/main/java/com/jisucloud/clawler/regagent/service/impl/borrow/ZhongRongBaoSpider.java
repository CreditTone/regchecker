package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZhongRongBaoSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "中融宝-互联网金融协会理事单位,平台合规运营,资金银行存管,为小微企业及个人提供多种类型的网络借贷信息中介服务。";
	}

	@Override
	public String platform() {
		return "zrbao";
	}

	@Override
	public String home() {
		return "zrbao.com";
	}

	@Override
	public String platformName() {
		return "中融宝";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.zrbao.com/User/phone.shtml";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("jsonDataSet", "{\"phone\":\""+account+"\"}")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.zrbao.com/password.shtml")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("手机号已被")) {
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

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15161509916");
	}

}
