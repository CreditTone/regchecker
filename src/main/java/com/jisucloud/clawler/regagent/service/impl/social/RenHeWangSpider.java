package com.jisucloud.clawler.regagent.service.impl.social;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "renhe.com", 
		message = "人和网是中国优秀的人脉拓展平台,在这里您不仅可以认识各行业的高端人脉,更可以通过人脉寻找各种投资和靠谱项目,推广您的产品或服务,寻找更好的职位,找到优秀人才!", 
		platform = "renhe", 
		platformName = "人和网", 
		tags = { "社交" , "人脉" }, 
		testTelephones = { "18810038000", "18210538513" })
public class RenHeWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.renhe.cn/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("pass", "xas1231")
	                .add("rememberPass", "true")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.renhe.cn/login.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			return res.contains("帐号或密码错误");
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
