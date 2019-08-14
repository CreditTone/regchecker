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
		home = "myerong.com", 
		message = "e融所官网资料是由网贷之家为您收集整理,想知道e融所可靠吗,e融所安全吗,e融所怎么样,可以来网贷之家e融所官网资料查询p2p平台数据、新闻、消息、口碑、点评。", 
		platform = "myerong", 
		platformName = "myerongName", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class ERongSuoSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.myerong.com/auth.action?callback=jQuery112781052600";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("accountName", account)
	                .add("method", "checkNameExists")
	                .add("subtime", "" + System.currentTimeMillis())
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.myerong.com")
					.addHeader("Referer", "https://www.myerong.com/sites/pages/register/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("true")) {
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
