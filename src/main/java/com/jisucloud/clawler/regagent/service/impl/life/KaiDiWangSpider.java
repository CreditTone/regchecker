package com.jisucloud.clawler.regagent.service.impl.life;

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
public class KaiDiWangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "凯迪网络(www.kdnet.net),伴随中国互联网和网民共同成长,从中国第一代网络论坛发轫,凯迪以“客观、公正、理性、宽容”为宗旨,聚集了一千多万中高端注册用户。";
	}

	@Override
	public String platform() {
		return "kdnet";
	}

	@Override
	public String home() {
		return "kdnet.net";
	}

	@Override
	public String platformName() {
		return "凯迪网络";
	}

	@Override
	public String[] tags() {
		return new String[] {"资讯", "社区", "论坛"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://i.kdnet.net/api/passport/query?kpjkey=jQuery11130013202867021713138_1563361732376&id="+account+"&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://uc.kdnet.net/?redirect=http%3A%2F%2Fclub.kdnet.net%2F#formRegister")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("手机号码已存在")) {
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
