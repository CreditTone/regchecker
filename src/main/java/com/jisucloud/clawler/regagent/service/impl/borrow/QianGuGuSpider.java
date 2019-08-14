package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "qiangugu.com", 
		message = "钱鼓鼓电商贷款平台,互联网金融P2P网络借贷平台,网商贷提供中小电商贷款、电商卖家贷款、网店贷款、京东贷款、天猫,淘宝店铺贷款及资金第三方存管,为网络投资理财用户和网络贷款用户提供透明、安全、高效的互联网金融服务。", 
		platform = "qiangugu", 
		platformName = "钱鼓鼓", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "18611216720", "18210538513" })
public class QianGuGuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.qiangugu.com/userajaxcheck/checkMobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qiangugu.com")
					.addHeader("Referer", "https://www.qiangugu.com/user/reg")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已注册")) {
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
