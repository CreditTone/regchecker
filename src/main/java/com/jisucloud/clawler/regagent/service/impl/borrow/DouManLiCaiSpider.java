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
		home = "mandofin.com", 
		message = "满兜(www.mandofin.com)于2015年8月上线运营,是杭州中廉网络科技有限公司旗下的互联网金融品牌,是由华景川集团战略投资、阿里高管创建。满兜一直秉持“金融创新。", 
		platform = "mandofin", 
		platformName = "mandofinName", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18210538513", "13810973590" })
public class DouManLiCaiSpider extends PapaSpider {


	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.mandofin.com/user/login.json";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("password", "xas230021nxz")
	                .add("captcha", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.mandofin.com/loginReg/login_h")
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
