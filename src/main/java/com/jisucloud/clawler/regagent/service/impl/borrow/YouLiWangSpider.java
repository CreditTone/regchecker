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
		home = "yooli.com", 
		message = "国内专业的P2P平台,6年交易总额突破850亿!坚持小额分散,提供专业、便捷、透明的咨询服务。项目丰富:无忧宝、定存宝、月息通,简单3步,免费注册,领新手专享限时福利!。", 
		platform = "yooli", 
		platformName = "有利网", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class YouLiWangSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.yooli.com/secure/ssoLogin.action";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("nickName", account)
	                .add("password", "x10xncodcos1")
	                .add("verifycode", "")
	                .add("chkboxautologin", "false")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.yooli.com")
					.addHeader("Referer", "https://www.yooli.com/secure/login/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("-88")) {
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
