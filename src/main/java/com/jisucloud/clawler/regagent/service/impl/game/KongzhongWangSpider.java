package com.jisucloud.clawler.regagent.service.impl.game;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "kongzhong.com", 
		message = "空中网是中国领先的网络游戏研发商和运营商,致力于为中国及海外互联网用户提供高品质的大型在线游戏服务,同时为中国手机用户提供多元化的无线娱乐服务。", 
		platform = "kongzhong", 
		platformName = "空中网", 
		tags = { "游戏" }, 
		testTelephones = { "15700102865", "18212345678" })
public class KongzhongWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.kongzhong.com/ajax/regcheckphone?clientid=useraccount&rand="+System.currentTimeMillis()+"&useraccount="+account+"&vcode=&personid=&bdFlag=0&phone="+account+"&_="+System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.kongzhong.com")
					.addHeader("Referer", "https://passport.kongzhong.com/acc")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("0")) {
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
