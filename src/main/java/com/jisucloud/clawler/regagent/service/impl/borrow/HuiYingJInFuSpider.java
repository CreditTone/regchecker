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
		home = "hyjf.com", 
		message = "汇盈金服为惠众商务旗下的互联网金融信息服务品牌，通过云计算、大数据等为用户提供创新性金融服务，为中小微企业、投资机构和个人提供投融资。", 
		platform = "hyjf", 
		platformName = "汇盈金服", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "13912345678", "18212345678" })
public class HuiYingJInFuSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.hyjf.com/user/findPassword/checkPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("telnum", account)
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.hyjf.com")
					.addHeader("Referer", "https://www.hyjf.com/user/findPassword")
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
