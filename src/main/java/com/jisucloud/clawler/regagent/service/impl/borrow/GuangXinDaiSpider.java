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
		home = "guangxindai.com", 
		message = "广信贷是国内安全、优质、规范的创新型科技金融信息服务平台,平台资金由江西银行存管,产品多样,1-36个月出借灵活,平均历史年化回报率12%,让您出借更加便捷、高效。", 
		platform = "guangxindai", 
		platformName = "广信贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class GuangXinDaiSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.guangxindai.com/ajax/register/phone_exist";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.guangxindai.com")
					.addHeader("Referer", "https://www.guangxindai.com/register")
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
