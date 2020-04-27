package com.jisucloud.clawler.regagent.service.impl.b2b;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "hc360.com", 
		message = "中国慧聪网,做生意就上慧聪网,360行,行行在慧聪.中国慧聪网 B2B诚信企业,1500万商家,海量供求信息任你选。", 
		platform = "hc360", 
		platformName = "慧聪网", 
		tags = { "b2b" ,"商机" ,"生意" }, 
		testTelephones = { "18515290717", "18212345678" })
public class CongHuiWangSpider extends PapaSpider {

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://reg.hc360.com/reg/check/bindmobile.html";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "reg.hc360.com")
					.addHeader("Referer", "https://reg.hc360.com/reg/info?sourcetypeid=1517")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("1")) {
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
