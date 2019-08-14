package com.jisucloud.clawler.regagent.service.impl.game;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "99.com", 
		message = "99.com数字娱乐门户集游戏门户、社区互动于一体，通过游戏官网、互动社区等频道，向广大用户提供多元化的互动娱乐内容和服务。", 
		platform = "99", 
		platformName = "99娱乐", 
		tags = { "游戏" }, 
		testTelephones = { "15956434943", "18210538513" })
public class _99Spider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://aq.99.com/V3/Handler/Default.ashx";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://aq.99.com/NDUser_Register_New.aspx")
					.post(createUrlEncodedForm("Action=check_username&Business=register&UserName="+account+"&RegType=3&Rnd=0.5016176569401889"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已经存在")) {
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
