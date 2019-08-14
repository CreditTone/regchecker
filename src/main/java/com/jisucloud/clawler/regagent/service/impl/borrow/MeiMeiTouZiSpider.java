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
		home = "meme2c.com", 
		message = "美美投资是鹏润控股旗下理财平台,专业的风控系统全程提供支持,银行资金存管全程资金流转透明,为投资者提供全面安全的资金保障.不同期限产品自由选择,适合各种投资需求。", 
		platform = "meme2c", 
		platformName = "美美投资", 
		tags = { "p2p", "理财" , "借贷" }, 
		testTelephones = { "18210538513", "15161500000" })
public class MeiMeiTouZiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.meme2c.com/findpswd/getMobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.meme2c.com/findpswd/loginPassword")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("false");
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
