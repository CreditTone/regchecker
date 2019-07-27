package com.jisucloud.clawler.regagent.service.impl.video;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class BaoFengYingYinSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "暴风影音致力打造大型互联网视频播放平台,集在线视频和本地播放服务于一体,是专注提供免费、高清网络视频服务的大型视频网站。";
	}

	@Override
	public String platform() {
		return "baofeng";
	}

	@Override
	public String home() {
		return "baofeng.com";
	}

	@Override
	public String platformName() {
		return "暴风影音";
	}

	@Override
	public String[] tags() {
		return new String[] {"影音", "视频", "MV"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15700102865", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://sso.baofeng.com/new/api/is_mobile_used?appid=8637&sign=de75e7a58d2fe0fb26bed9f2909d52595cf8ee90&mobile="+account+"&callback=jQuery112402612518137038946_"+System.currentTimeMillis()+"&_=" +System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "sso.baofeng.com")
					.addHeader("Referer", "http://www.baofeng.com/")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("is_used\":1") || res.contains("is_used\": 1")) {
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
