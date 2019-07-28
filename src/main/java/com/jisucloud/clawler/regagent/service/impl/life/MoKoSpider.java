package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class MoKoSpider extends PapaSpider {


	@Override
	public String message() {
		return "美空是唯一一家致力于专业娱乐导向的网络平台。美空为广告/时装（男,女模特），歌/舞剧/影视演员，摄影/造型师，视觉设计等人选及相关专业机构提供展示与合作平台。";
	}

	@Override
	public String platform() {
		return "moko";
	}

	@Override
	public String home() {
		return "moko.com";
	}

	@Override
	public String platformName() {
		return "美空";
	}

	@Override
	public String[] tags() {
		return new String[] {"模特", "时装" , "演员" , "摄影师" , "造型师"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18523857478", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://www.moko.cc/register|checkPhoneIsUse.action?phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.moko.cc/user/register/G.html")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().contains("register.error.phone");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
