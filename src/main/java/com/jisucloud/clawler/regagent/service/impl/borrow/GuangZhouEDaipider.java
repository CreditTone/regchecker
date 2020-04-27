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
		home = "gzdai.com", 
		message = "广州e贷(www.gzdai.com)是上市公司参股的P2P平台,同时也是广州互联网金融协会会长单位,由中国服务业企业500强、广东省民营企业100强、广州物流与供应链协会会长单位。", 
		platform = "gzdai", 
		platformName = "广州e贷", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class GuangZhouEDaipider extends PapaSpider {


	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.gzdai.com/user/proving.do?type=phone&param=" + account;
			FormBody formBody = new FormBody
	                .Builder()
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("Referer", "https://www.gzdai.com/user/register.jspx")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被")) {
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
