package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class JuAiCaiSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "聚爱财专业的P2P投资平台、P2P网贷投资、互联网金融平台,面向个人投资者提供P2P投资产品、固定收益类产品;专业金融机构担保,100元起投,投资周期1~12个月,每日计息。";
	}

	@Override
	public String platform() {
		return "juaicai";
	}

	@Override
	public String home() {
		return "juaicai.com";
	}

	@Override
	public String platformName() {
		return "聚爱财";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "理财" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910002045", "18210538513");
	}
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(JuAiCaiSpider.class);
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.juaicai.cn/isNewUser?phoneno=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "www.juaicai.cn")
					.addHeader("Referer", "https://www.juaicai.cn/pc_login/?isReg=y")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			return !res.equals("1");
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
