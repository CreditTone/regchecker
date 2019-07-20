package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class HuangHeJinRongSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "黄河金融是于2014年3月24日上线，是浙江众联在线资产管理有限公司旗下的互联网金融信息服务平台，结合利用平台的特点，使得黄河金融具有“零风险”、“稳收益”、“高流动”特点，满.足了广.大个人用户的理财需求。";
	}

	@Override
	public String platform() {
		return "hhedai";
	}

	@Override
	public String home() {
		return "hhedai.com";
	}

	@Override
	public String platformName() {
		return "黄河金融";
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
			String url = "https://www.hhedai.com/?user&q=getpwd&type=getphonecode&https=false&phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.hhedai.com")
					.addHeader("Referer", "https://www.hhedai.com/secure/register.html")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
