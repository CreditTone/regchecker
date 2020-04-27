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
		home = "wjs.com", 
		message = "网金社是由恒生电子、蚂蚁金服、中投保共同设立的浙江互联网金融资产交易中心股份有限公司推出的互联网金融平台。 ", 
		platform = "wjs", 
		platformName = "网金社", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class WangJinSheSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.wjs.com/web/login/mobileLogin";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobileNo", account)
	                .add("password", "2dd573568d7358ef618ce940565c5b7b8cc0b2ee7b8f2916f2b6b91aed0e4561ce70ccb76b9f4604f725ead9a6483a18fb05bbfa093a90ce6f84651c33292acec95c2e698a18f2c4ba9f7d5bcadeae783afa261cb4cd8a2ac426efc82d1cb632f63bc0c46adb5ab7e62911b5431d7ab1b76f29fee4f12a63700a6c448fb6eb15")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "www.wjs.com")
					.addHeader("Referer", "https://www.wjs.com/web/login/index")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			return res.contains("密码不正确");
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
