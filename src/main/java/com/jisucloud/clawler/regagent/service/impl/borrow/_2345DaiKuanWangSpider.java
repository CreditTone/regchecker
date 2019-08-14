package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Random;



@Slf4j
@PapaSpiderConfig(
		home = "2345.com", 
		message = "上海二三四五网络科技股份有限公司(以下简称“2345”),早在2014年6月就已上市,而旗下的现金贷业务,包括2345贷款王、即刻贷以及雷霆贷。", 
		platform = "2345dk", 
		platformName = "2345贷款王", 
		tags = { "贷超", "消费分期" , "借贷" }, 
		testTelephones = { "18210538513", "15161509916" },
		excludeMsg = "不做了"
		)
public class _2345DaiKuanWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://wsdaikuan.2345.com/b2b2p-ws/api/v5_9_1/isRegistered?version=5.13.0&scene=0220";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Loan/5.13.0(os:android)-865166024506316")
					.addHeader("Host", "wsdaikuan.2345.com")
					.addHeader("channel", "1003")
					.addHeader("Authorization", "Basic c3VpeGluZGFpOjFxYXohQCMk")
					.addHeader("X-DeviceNo", "865166024506316")
					.addHeader("os", "android")
					.addHeader("productId", "-1")
					.addHeader("X-Lat", "39.916409")
					.addHeader("version", "5.13.0")
					.addHeader("X-DeviceToken", "865166024506316")
					.addHeader("X-Ip", new Random().nextInt(255)+ ".16."+new Random().nextInt(255)+".15")
					.addHeader("app-bundle-id", "com.hyron.b2b2p")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string()).getJSONObject("result");
			if (result.getBooleanValue("registered")) {
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
