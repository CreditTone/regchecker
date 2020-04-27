package com.jisucloud.clawler.regagent.service.impl.borrow;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "ddxlong.com", 
		message = "贷贷兴隆隶属于重庆兴农鑫电子商务有限公司。重庆兴农鑫电子商务有限公司经重庆市工商局登记注册于2013年成立，位于重庆市渝中区青年路7号43-5#，注册资本金2000万元人民币。", 
		platform = "ddxlong", 
		platformName = "贷贷兴隆", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class DaiDaiXingLongSpider extends PapaSpider {

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

}
