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
		home = "zrbao.com", 
		message = "中融宝-互联网金融协会理事单位,平台合规运营,资金银行存管,为小微企业及个人提供多种类型的网络借贷信息中介服务。", 
		platform = "zrbao", 
		platformName = "中融宝", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class ZhongRongBaoSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.zrbao.com/User/phone.shtml";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("jsonDataSet", "{\"phone\":\""+account+"\"}")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.zrbao.com/password.shtml")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("手机号已被")) {
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
