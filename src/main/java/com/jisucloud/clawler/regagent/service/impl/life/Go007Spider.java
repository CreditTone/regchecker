package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;

@Slf4j
@UsePapaSpider
public class Go007Spider extends PapaSpider {
	
	private Session session = JJsoup.newSession();

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

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "user.go007.com");
		headers.put("Referer", "http://user.go007.com/Register.aspx");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://user.go007.com/ajaxhandler.ashx?Phone="+account+"&action=CheckIfExistsPhone";
			Connection.Response response = session.connect(url)
					.headers(getHeader()).ignoreContentType(true).execute();
			return response.body().contains("1");
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
