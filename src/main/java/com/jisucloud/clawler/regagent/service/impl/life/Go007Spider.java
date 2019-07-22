package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Slf4j
@UsePapaSpider
public class Go007Spider extends PapaSpider {
	
	@Override
	public String message() {
		return "go007城际分类网免费为城市居民提供生活分类信息发布和分享平台,是中国最好的分类信息网站、分类广告推广效果最好的网站之一;在此您无需注册就可以浏览和发布房产。";
	}

	@Override
	public String platform() {
		return "go007";
	}

	@Override
	public String home() {
		return "go007.com";
	}

	@Override
	public String platformName() {
		return "城际分类网";
	}

	@Override
	public String[] tags() {
		return new String[] {"o2o", "生活休闲", "求职" , "招聘" , "房产家居"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13925306966", "18210538513");
	}

	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "user.go007.com");
		headers.put("Referer", "http://user.go007.com/Register.aspx");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return Headers.of(headers);
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://user.go007.com/ajaxhandler.ashx?Phone="+account+"&action=CheckIfExistsPhone";
			Request request = new Request.Builder().url(url)
        			.headers(getHeader())
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("1");
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
