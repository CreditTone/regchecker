package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep007.spiderbase.util.StringUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "yueus.com", 
		message = "约约是一个高效的时间电商平台，用户可以通过约约平台进行约拍、约摄影、约模特或者参加陶艺、烘焙、茶艺、插花、绘画等手工培训，利用自己的零碎时间来创造价值。", 
		platform = "yueus", 
		platformName = "约约网", 
		tags = { "摄影", "约拍", "陶艺" , "茶艺" }, 
		testTelephones = { "18523857478", "18210538513" })
public class YueYueSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.yueus.com/action/new_general_login_op.php?callback=jQuery11020953599334789923_"+System.currentTimeMillis()+"&phone="+account+"&password=dasdas1231&wx_bind=0&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://www.yueus.com/passport/login.php")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			return res.contains("密码错误");
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
