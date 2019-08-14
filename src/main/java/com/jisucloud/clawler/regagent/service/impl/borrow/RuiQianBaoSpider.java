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
		home = "rqbao.com", 
		message = "国瑞泰旗下瑞钱宝，由国企参股的专业互联网金融理财平台，平台整合金融资源并结合互联网技术，推出适合不同投资者的投资理财产品来满足广大投资者的理财需要，致力于为理财的投资者提供一个全方位。", 
		platform = "rqbao", 
		platformName = "瑞钱宝", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "15837333644", "15161509916" })
public class RuiQianBaoSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.rqbao.com/member/verifyMobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "http://www.rqbao.com/member/findPasswordForUser")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("false")) {
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
