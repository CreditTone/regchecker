package com.jisucloud.clawler.regagent.service.impl.work;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class DajieWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "大街网创立于2008年底,是一家真正专属于年轻人的移动社交招聘平台,为年轻职场人匹配最佳工作机会,拓展职场人脉,提升职场价值.大街想要做的,就是用互联网思维。";
	}

	@Override
	public String platform() {
		return "dajie";
	}

	@Override
	public String home() {
		return "dajie.com";
	}

	@Override
	public String platformName() {
		return "大街网";
	}

	@Override
	public String[] tags() {
		return new String[] {"求职" , "招聘" , "商务"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18515290717", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.dajie.com/account/phonestatuscheck?callback=jQuery151020488464963648478_"+System.currentTimeMillis()+"&ajax=1&phoneNumber="+account+"&_=1559213156444&_CSRFToken=";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.dajie.com")
					.addHeader("Referer", "https://www.dajie.com")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("AUTHED")) {
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
