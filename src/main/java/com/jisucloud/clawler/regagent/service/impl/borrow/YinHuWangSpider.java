package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "yinhu.com", 
		message = "银湖网成立于2014年,由A股上市公司熊猫金控【股票代码:600599】全资控股,注册资本2亿元。厦门银行存管,健全的风控体系,新手送636红包,让您享受快捷、透明的理财服务!", 
		platform = "yinhu", 
		platformName = "银湖网", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class YinHuWangSpider extends PapaSpider {
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.yinhu.com/ajax_check_exist_user.bl";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobileNo", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.yinhu.com")
					.addHeader("Referer", "https://www.yinhu.com/user/go_regist.bl")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
