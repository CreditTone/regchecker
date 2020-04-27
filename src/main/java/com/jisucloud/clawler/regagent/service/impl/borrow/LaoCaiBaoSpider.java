package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "laocaibao.com", 
		message = "捞财宝(laocaibao.com) -证大集团旗下网络借贷信息中介平台，银行资金存管，信息安全三级等保、AAA级信用认证平台，股东证大集团26年金融投资背景，近8年小额信贷领域专业经验。坚持小额分散，信息真实透明，出借省心。", 
		platform = "laocaibao", 
		platformName = "捞财宝", 
		tags = { "P2P", "小额信贷" , "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class LaoCaiBaoSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.laocaibao.com/register/notRegistered";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("cellphone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.laocaibao.com")
					.addHeader("Referer", "https://www.laocaibao.com/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return !result.getBooleanValue("data");
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
