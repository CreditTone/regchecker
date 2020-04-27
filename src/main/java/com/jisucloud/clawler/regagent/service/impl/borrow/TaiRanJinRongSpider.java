package com.jisucloud.clawler.regagent.service.impl.borrow;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "trc.com", 
		message = "泰然金融-泰和网(www.trc.com) - 行业领先的网络借贷信息中介机构,为用户提供便捷、安全、透明、高效、低门槛的互联网金融综合服务,由银行提供资金存管,安全有保障!", 
		platform = "trc", 
		platformName = "泰然金融", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class TaiRanJinRongSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.trc.com/proxy/account/user/phone/exist/" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://passport.trc.com/popupReg/inner.html")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("true")) {
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
