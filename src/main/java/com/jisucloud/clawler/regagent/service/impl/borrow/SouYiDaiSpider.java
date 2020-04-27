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
		home = "souyidai.com", 
		message = "搜易贷(souyidai.com)搜狐集团(NASDAQ：SOHU)旗下网络借贷信息中介服务平台，中国互联网金融协会理事单位。华夏银行资金存管，国家信息安全等级保护三级认证，为用户提供专业的网络借贷信息服务——产品丰富，期限多样，更有新手项目！", 
		platform = "souyidai", 
		platformName = "搜易贷", 
		tags = { "P2P", "理财" , "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class SouYiDaiSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.souyidai.com/regist/isUsernameUsed";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.souyidai.com")
					.addHeader("Referer", "https://www.souyidai.com/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已注册")) {
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
