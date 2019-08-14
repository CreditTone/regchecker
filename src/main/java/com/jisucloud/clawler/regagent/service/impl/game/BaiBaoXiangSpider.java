package com.jisucloud.clawler.regagent.service.impl.game;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "jdbbx.com", 
		message = "简单百宝箱是一个免费、绿色和安全的游戏软件分享平台。简单百宝箱坚持免费、绿色的设计理念,按照游戏玩家使用习惯精心打造,免除玩家寻找游戏软件或者游戏攻略的烦恼。", 
		platform = "jdbbx", 
		platformName = "简单百宝箱", 
		tags = { "游戏" }, 
		testTelephones = { "18511912334", "18210538513" })
public class BaiBaoXiangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://user.jdbbx.com/Register.aspx";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userName", account)
	                .add("Action", "DoJudgeUserName")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "user.jdbbx.com")
					.addHeader("Referer", "http://user.jdbbx.com/Register.aspx")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("103")) {
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
