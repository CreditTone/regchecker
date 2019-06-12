package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class _17KSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "17K小说网(17k.com)创建于2006年,原名一起看小说网,是中文在线旗下集创作、阅读于一体的在线阅读网站。我们以“让每个人都享受创作的乐趣”为使命,提供玄幻奇幻。";
	}

	@Override
	public String platform() {
		return "17k";
	}

	@Override
	public String home() {
		return "17k.com";
	}

	@Override
	public String platformName() {
		return "17K小说网";
	}

	@Override
	public String[] tags() {
		return new String[] {"电子书", "阅读" , "小说"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new _17KSpider().checkTelephone("18720982607"));
//		System.out.println(new _17KSpider().checkTelephone("18210538513"));
//	}

	@Override
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
