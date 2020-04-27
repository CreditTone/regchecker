package com.jisucloud.clawler.regagent.service.impl.video;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "le.com", 
		message = "乐视TV,是乐视网专门为用户打造的一款在线视频播放应用,其适用于智能电视和智能盒子。依托乐视网强大的版权优势,拥有海量正版影视,内容涵盖电影、电视剧、动漫。", 
		platform = "letv", 
		platformName = "乐视TV", 
		userActiveness = 0.6f,
		tags = { "视频", "影音" }, 
		testTelephones = { "18720982607", "18212345678" })
public class LeTVSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://sso.le.com/user/checkMobileExists/mobile/"+account+"?jsonp=jQuery191074759928924606&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "sso.le.com")
					.addHeader("Referer", "https://sso.le.com/user/mobilereg?ver=3.0&lang=zh-cn&country=CN&plat=www&next_action=http%3A%2F%2Fwww.le.com%2F")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("已存在")) {
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
