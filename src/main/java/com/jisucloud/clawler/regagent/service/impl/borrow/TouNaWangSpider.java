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
		home = "touna.com", 
		message = "投哪网2012年正式上线，荣获广发信德A轮，大金重工B轮，巨加网络C轮8.2亿融资，2019年投哪网实缴资本增至5亿，新手注册即送350元红包，6月出借奖励1098元，投哪网合规运营多年，资金银行存管，大数据风控团队，提供专业、合规的金融服务，用专业铸造信赖。", 
		platform = "touna", 
		platformName = "投哪网", 
		tags = { "P2P" , "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class TouNaWangSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.touna.cn/auth.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("method", "isExistPhone")
	                .add("inviteUserId", "")
	                .add("subtime", System.currentTimeMillis()+"")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A402 Safari/604.1")
					.addHeader("Host", "m.touna.cn")
					.addHeader("Referer", "https://m.touna.cn/new/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getBooleanValue("result")) {
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
