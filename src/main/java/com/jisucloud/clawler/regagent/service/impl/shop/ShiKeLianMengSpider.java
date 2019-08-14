package com.jisucloud.clawler.regagent.service.impl.shop;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "shikee.com", 
		message = "试客联盟是获得央视报道的试用网站,是试客首选的免费试用网和试客网,试用通过率高，所有试用品免费试用，不用花钱,试用商品后无需退还,试客联盟为试客提供最实用的免费试用品!", 
		platform = "shikee", 
		platformName = "试客联盟", 
		tags = { "购物" ,"试客" ,"9.9包邮" }, 
		testTelephones = { "18515290717", "18210538513" })
public class ShiKeLianMengSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://ucenter.shikee.com/findpwd/check_account";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "ucenter.shikee.com")
					.addHeader("Referer", "http://ucenter.shikee.com/findpwd/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("true")) {
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
