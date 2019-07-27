package com.jisucloud.clawler.regagent.service.impl.game;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class XubeiSpider extends PapaSpider {

	@Override
	public String message() {
		return "虚贝网是国内专业便捷游戏租号借号平台，平台有5万个极品游戏账号供您选择，绝地求生租号、CF租号、LOL租号、王者荣耀租号、逆战租号、QQ飞车租号等端游手游均已开通，steam平台游戏抢先体验。";
	}

	@Override
	public String platform() {
		return "xubei";
	}

	@Override
	public String home() {
		return "xubei.com";
	}

	@Override
	public String platformName() {
		return "虚贝网";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏", "租号玩"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15188533909", "18210538513");
	}


	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://passport-server.xubei.com/reg/isPhoneExist?mobile=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://passport.xubei.com/register.html")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已注册")) {
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
