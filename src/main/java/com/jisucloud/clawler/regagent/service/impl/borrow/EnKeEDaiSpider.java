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
		home = "ek360.com", 
		message = "恩科e贷是一家专注于不动产抵押的P2P网贷平台。打造河北P2P网贷平台、石家庄P2P网贷平台及金融供应链金融,互联网金融服务。致力于引领和推进行业进步,合国内市场的。", 
		platform = "ek360", 
		platformName = "ek360Name", 
		tags = { "P2P" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class EnKeEDaiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.ek360.com/member/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("password", "maoab2Q11ile")
	                .add("", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.ek360.com")
					.addHeader("Referer", "https://www.ek360.com/#/login")
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
