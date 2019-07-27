package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

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
public class ZhuBaoDaiSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "珠宝贷是国内专注珠宝行业的网贷、出借服务平台,是拥有上市公司及多家知名珠宝企业股东背景的网络信息中介机构。";
	}

	@Override
	public String platform() {
		return "zhubaodai";
	}

	@Override
	public String home() {
		return "zhubaodai.com";
	}

	@Override
	public String platformName() {
		return "珠宝贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.zhubaodai.com/login/login/ajaxValidate.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("vCode", account)
	                .add("vType", "1")
	                .add("vCodeType", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.zhubaodai.com/login/login/register.do")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("USER_EXIST")) {
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
