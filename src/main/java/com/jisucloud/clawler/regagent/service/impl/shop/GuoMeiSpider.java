package com.jisucloud.clawler.regagent.service.impl.shop;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "gome.com", 
		message = "国美电器（GOME）成立于1987年1月1日，总部位于香港，是中国大陆家电零售连锁企业。2009年入选中国世界纪录协会中国最大的家电零售连锁企业。", 
		platform = "gome", 
		platformName = "国美电器", 
		tags = { "电商" , "电器" }, 
		testTelephones = { "18210538000", "18210538513" })
public class GuoMeiSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.gomefinance.com.cn/api/v1/checkMobile?t=" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.gomefinance.com.cn")
					.addHeader("channel", "GMJRBDSSPPC")
					.addHeader("plant", "PC")
					.addHeader("Referer", "https://www.gomefinance.com.cn/src/html/register.html")
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
