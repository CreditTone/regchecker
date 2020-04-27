package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;



import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "juaicai.com", 
		message = "聚爱财专业的P2P投资平台、P2P网贷投资、互联网金融平台,面向个人投资者提供P2P投资产品、固定收益类产品;专业金融机构担保,100元起投,投资周期1~12个月,每日计息。", 
		platform = "juaicai", 
		platformName = "聚爱财", 
		tags = { "p2p", "理财" , "借贷" }, 
		testTelephones = { "13910002045", "18212345678" },
		ignoreTestResult = true)
public class JuAiCaiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.juaicai.cn/isNewUser?phoneno=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "www.juaicai.cn")
					.addHeader("Referer", "https://www.juaicai.cn/pc_login/?isReg=y")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			return !res.equals("1");
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
