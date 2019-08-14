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
		home = "kuaikuaidai.com", 
		message = "快快贷投资平台搭建全新互联网投资模式,为投资提供互联网金融平台。互联网投资是银行活期投资的10倍以上,超低门槛,超高收益。", 
		platform = "kuaikuaidai", 
		platformName = "快快贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class KuaiKuaiDaiSpider extends PapaSpider {
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.kuaikuaidai.com/customer/checkCustomerPhone.do?phone=param";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("param", account)
	                .add("name", "phone")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.kuaikuaidai.com")
					.addHeader("Referer", "https://www.kuaikuaidai.com/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已注册")) {
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
