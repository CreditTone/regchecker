package com.jisucloud.clawler.regagent.service.impl.education;

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
public class QiDianSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "起点中文网创建于2002年5月，是国内最大文学阅读与写作平台之一，是国内领先的原创文学门户网站，隶属于国内最大的数字内容综合平台——阅文集团旗下。";
	}

	@Override
	public String platform() {
		return "qidian";
	}

	@Override
	public String home() {
		return "qidian.com";
	}

	@Override
	public String platformName() {
		return "起点小说";
	}

	@Override
	public String[] tags() {
		return new String[] {"电子书", "小说" , "网络原创"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18660390000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://ptlogin.yuewen.com/userSdk/checkaccount?method=jQuery191021014016847558348_"+System.currentTimeMillis()+"&appId=10&format=jsonp&account="+account+"&accountType=101&areaId=1&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "ptlogin.yuewen.com")
					.addHeader("Referer", "https://passport.qidian.com/reg.html?appid=10&areaid=1&target=iframe&ticket=1&auto=1&autotime=30&returnUrl=https%3A%2F%2Fwww.qidian.com")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("existing\":true")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
