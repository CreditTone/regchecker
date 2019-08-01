package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider(ignoreTestResult = true)
public class BangBangTangSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "邦帮堂由北京紫貔财富网络科技有限公司运营，坚持小额普惠原则，为用户提供网络借贷信息中介服务。";
	}

	@Override
	public String platform() {
		return "rmbbox";
	}

	@Override
	public String home() {
		return "rmbbox.com";
	}

	@Override
	public String platformName() {
		return "邦帮堂";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷", "贷超"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.rmbbox.com/register/checkMobile?enterprise=true&userRole=BORROWERS&mobile=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.rmbbox.com/register")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被")) {
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
