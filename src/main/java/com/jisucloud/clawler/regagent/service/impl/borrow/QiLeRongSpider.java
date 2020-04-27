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
		home = "qilerong.com", 
		message = "奇乐融(www.qilerong.com)于2015年10月28日上线,由安徽唯源金融信息服务有限公司负责运营,以“践行普惠金融,助力财富增值,支持实体经济”为使命,致力于为中小微。", 
		platform = "qilerong", 
		platformName = "奇乐融", 
		tags = { "p2p", "借贷" , "小微借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class QiLeRongSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.qilerong.com/api/v2/users/check/mobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.qilerong.com/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("MOBILE_EXISTS")) {
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
