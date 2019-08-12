package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class DaiDaiXingLongSpider extends PapaSpider {

	@Override
	public String message() {
		return "贷贷兴隆隶属于重庆兴农鑫电子商务有限公司。重庆兴农鑫电子商务有限公司经重庆市工商局登记注册于2013年成立，位于重庆市渝中区青年路7号43-5#，注册资本金2000万元人民币。";
	}

	@Override
	public String platform() {
		return "ddxlong";
	}

	@Override
	public String home() {
		return "ddxlong.com";
	}

	@Override
	public String platformName() {
		return "贷贷兴隆";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.ddxlong.com/Home/CheckNewTel";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("param", account)
	                .add("name", "Phone_nvarchar")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.ddxlong.com/Home/Register")
					.post(formBody)
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
