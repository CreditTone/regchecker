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
		home = "xitouwang.com", 
		message = "喜投网(www.xitouwang.com)是深圳市喜投金融服务有限公司旗下互联网金融信息服务平台,致力于通过互联网金融参与中国利率市场化改革,以让普天之下的老百姓和小微。", 
		platform = "xitouwang", 
		platformName = "喜投网", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18210538513", "15161509916" })
public class XiTouWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.xitouwang.com/user/loginSubmit";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("password", "sad1232sa")
	                .add("remember", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.xitouwang.com/user/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码错误")) {
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
