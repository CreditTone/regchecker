package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider(ignoreTestResult = true)
public class YiDaiWangSpider extends PapaSpider {

	
	
	@Override
	public String message() {
		return "宜贷网是软银(中国)注资的P2P平台,平台成立于2014年1月,已运营四年之久。宜贷网以透明、专业为宗旨,为用户提供有温度的网络借贷信息中介服务。";
	}

	@Override
	public String platform() {
		return "yidai";
	}

	@Override
	public String home() {
		return "yidai.com";
	}

	@Override
	public String platformName() {
		return "宜贷网";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.yidai.com/user/checkphone/?phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.yidai.com")
					.addHeader("Referer", "https://www.yidai.com/user/register/")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			System.out.println(res);
			if (!res.equals("0")) {
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
