package com.jisucloud.clawler.regagent.service.impl.life;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "228.com", 
		message = "永乐票务-演出门票平价,热卖!为您精选演唱会/话剧/音乐会等特色演出!同价位占好座!24小时票务网上在线预订!快捷,让您享受优质的购票体验!中国票务行业上市企业!", 
		platform = "228", 
		platformName = "永乐票务", 
		tags = { "娱乐", "演唱会" }, 
		testTelephones = { "18611216720", "18210538513" })
public class YongLePiaoWuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.228.com.cn/ajax/findPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.228.com.cn")
					.addHeader("Referer", "https://www.228.com.cn/customer/reg.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("flag\":0") || res.contains("flag\": 0")) {
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
