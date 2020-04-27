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
		home = "puhuijia.com", 
		message = "普惠家(www.puhuijia.com)专业的P2P互联网金融信息服务平台。旨在为有财富增值需求的出借人和有融资需求的借款人提供专业、高效、诚信、互惠互助的网络投融资信息。", 
		platform = "puhuijia", 
		platformName = "普惠家", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class PuHuiJiaSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.puhuijia.com/member/memberRegistryAction!validatememberPhone.action";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("memberPhone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.puhuijia.com")
					.addHeader("Referer", "https://www.puhuijia.com/member/memberRegistryAction!initRegistry.action")
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
