package com.jisucloud.clawler.regagent.service.impl.photo;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class WoTuWangSpider extends PapaSpider {

	@Override
	public String message() {
		return "我图网是为用户提供正版设计作品交易的平台，专注正版设计作品交易，包括背景墙素材、PPT模板、淘宝素材、视频素材等等设计作品，下载正版设计作品就到我图网。";
	}

	@Override
	public String platform() {
		return "ooopic";
	}

	@Override
	public String home() {
		return "ooopic.com";
	}

	@Override
	public String platformName() {
		return "我图网";
	}

	@Override
	public String[] tags() {
		return new String[] {"原创" , "设计", "素材"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13991808887", "13811085745");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.ooopic.com/user/loginAction.php?action=verifyRegAccount&username="+account+"&regType=phone";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://account.ooopic.com/user/login.php?type=reg&from=top&host=www.ooopic.com&request_uri=/")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已存在")) {
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
