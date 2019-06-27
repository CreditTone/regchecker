package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class DuoDuoJinRongSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "朵朵金融由多年从业于金融,互联网行业的资深团队倾力打造,秉承合规、合法的经营理念,严格遵守相关监管条例,持续改善用户体验,以互联网技术为工具为广大用户提供更优质!";
	}

	@Override
	public String platform() {
		return "duoduojr";
	}

	@Override
	public String home() {
		return "duoduojr.com";
	}

	@Override
	public String platformName() {
		return "朵朵金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new DuoDuoJinRongSpider().checkTelephone("15985268904"));
		System.out.println(new DuoDuoJinRongSpider().checkTelephone("18210538513"));
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://portal.duoduojr.cn/member/login.htm";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .add("password", "paramMa1email")
	                .add("checkCode", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "portal.duoduojr.cn")
					.addHeader("Referer", "https://www.duoduojr.cn/login.html?redirectURL=aHR0cHM6Ly93d3cuZHVvZHVvanIuY24v")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码错误")) {
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
