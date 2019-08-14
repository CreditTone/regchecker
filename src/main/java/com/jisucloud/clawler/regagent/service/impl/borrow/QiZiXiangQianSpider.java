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
		home = "qzxq.com", 
		message = "奇子向钱，是北京奇子投资管理有限公司旗下的互联网金融综合服务商,与奇子贷、资产卫士共同构成奇子金服的三大业务模块，专注于为投资者提供真实、透明、便捷的互联网理财及增值服务。", 
		platform = "qzxq", 
		platformName = "奇子向钱", 
		tags = { "P2P" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class QiZiXiangQianSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.qzxq.com/regCtrl/isLoginNameExist.do?0.8" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("loginName", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qzxq.com")
					.addHeader("Referer", "https://www.qzxq.com/regCtrl/register.do")
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
