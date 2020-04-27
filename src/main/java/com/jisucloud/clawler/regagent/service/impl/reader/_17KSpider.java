package com.jisucloud.clawler.regagent.service.impl.reader;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "17k.com", 
		message = "17K小说网(17k.com)创建于2006年,原名一起看小说网,是中文在线旗下集创作、阅读于一体的在线阅读网站。我们以“让每个人都享受创作的乐趣”为使命,提供玄幻奇幻。", 
		platform = "17k", 
		platformName = "17K小说网", 
		tags = { "电子书", "阅读" , "小说" }, 
		testTelephones = { "18720982607", "18212345678" })
public class _17KSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.17k.com/ck/user/reset/info?loginName="+account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.17k.com")
					.addHeader("Referer", "https://passport.17k.com/password/")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("loginName")) {
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
