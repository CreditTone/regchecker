package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UsePapaSpider
@Slf4j
public class WangdaiTianYanSpider extends PapaSpider {

	@Override
	public String message() {
		return "网贷天眼资讯频道为用户提供网贷新闻头条、p2p理财网贷资讯,互联网金融行业新闻,包含有网贷政策、网贷行业、平台动态、行业观点、互联网金融等内容.";
	}

	@Override
	public String platform() {
		return "p2peye";
	}

	@Override
	public String home() {
		return "p2peye.com";
	}

	@Override
	public String platformName() {
		return "网贷天眼";
	}

	@Override
	public String[] tags() {
		return new String[] {"金融资讯", "贷超" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538577", "18210538513");
	}

	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "www.p2peye.com");
		headers.put("Referer", "https://www.p2peye.com/member.php?mod=register");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return Headers.of(headers);
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.p2peye.com/forum.php?mod=ajax&inajax=yes&infloat=register&handlekey=register&ajaxmenu=1&action=checkemobile_json&phonenum=" + account;
			Request request = new Request.Builder().url(url)
					.headers(getHeader())
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("7053");
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
