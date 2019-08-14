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
		home = "qianxiangbank.com", 
		message = "钱香金融专注黄金珠宝小微金融,是知名创投和产业资本联袂打造的小微金融借贷撮合服务的互联网金融信息中介平台,助力黄金珠宝产业链供应链金融,为品牌零售门店提供小微。", 
		platform = "qianxiangbank", 
		platformName = "钱香金融", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class QianXiangSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.qianxiangbank.com/home/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("name", account)
	                .add("password", "mobi12dasa2le")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qianxiangbank.com")
					.addHeader("Referer", "https://www.qianxiangbank.com/home/gotoLogin")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("falsePwd")) {
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
