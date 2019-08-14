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
		home = "51jiecai.com", 
		message = "今日捷财是上海捷财金融信息服务有限公司旗下一个独立公正、真实透明的金融信息服务平台，是中国互联网金融协会会员单位、上海市互联网金融行业协会会员单位，为融资人提供一个快速获取低成本资金的融资平台。 ", 
		platform = "51jiecai", 
		platformName = "今日捷财", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18210538513", "15161509916" })
public class JinRiJieCaiSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.51jiecai.com/passport/api/wp/sms/forgetLoginPwd.html?app=4&did=&uid=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.51jiecai.com/passport/reg/index.html")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("success")) {
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
