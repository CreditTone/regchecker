package com.jisucloud.clawler.regagent.service.impl.borrow;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "168p2p.com", 
		message = "168金服(客服热线400-8888-366转2),是全国三大财经门户之一、工信部互联网百强企业“中金在线”旗下理财平台,为广大出借者提供基于汽车金融的理财产品的P2P理财出借。", 
		platform = "168p2p", 
		platformName = "168金服", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18210538513", "15161509916" })
public class _168JinFuSpider extends PapaSpider {
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.168p2p.com/168P2P-pc/front/user/checkUnique.jhtml";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.168p2p.com/168P2P-pc/front/user/register.jhtml")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被注册")) {
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
