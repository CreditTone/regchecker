package com.jisucloud.clawler.regagent.service.impl.photo;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "zcool.com.cn", 
		message = "站酷网（www.zcool.com.cn），中国最具人气的大型综合性设计网站，聚集了中国绝大部分的专业设计师、艺术院校师生、潮流艺术家等年轻创意人群，是国内最活跃的原创设计交流平台。会员交流涉及。", 
		platform = "zcool", 
		platformName = "站酷网", 
		tags = { "原创" , "设计" }, 
		testTelephones = { "18515290000", "13811085745" })
public class ZhanKuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.zcool.com.cn/login_jsonp_active.do?jsonpCallback=jQuery1910678351554&appId=1006&username="+account+"&password=96f4fcff6034831575b142f85ecd007071ef3325b6d6444310c17c67bf882035&autoLogin=1&code=&service=https%3A%2F%2Fmy.zcool.com.cn%2Ffocus%2Factivity&appLogin=https%3A%2F%2Fwww.zcool.com.cn%2Flogin_cb&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://passport.zcool.com.cn/loginApp.do?appId=1006&cback=https://my.zcool.com.cn/focus/activity")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("错误") || res.contains("请输入验证码")) {
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
