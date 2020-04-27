package com.jisucloud.clawler.regagent.service.impl.social;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;
import java.util.Random;


@Slf4j
@PapaSpiderConfig(
		home = "jiayuan.com", 
		message = "世纪佳缘是一个严肃的婚恋网站，网站规模大、征友效果反响较好，通过互联网平台和线下会员见面活动为中国大陆、香港、澳门、台湾及世界其它国家和地区的单身人士提供严肃婚恋交友服务。", 
		platform = "jiayuan", 
		platformName = "世纪佳缘", 
		tags = { "单身交友" , "婚恋" }, 
		userActiveness = 0.7f,
		testTelephones = { "18810038000", "18212345678" })
public class ShiJiJiaYuanSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://uapi.jiayuan.com/Api/Reglogin/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("platform", "jiayuan")
	                .add("osv", "4.4.2")
	                .add("mac", "08%3A6D%3A41%3AD5%3A7A%3A6A")
	                .add("ver", "1.0")
	                .add("lang", "zh_CN")
	                .add("traceid", "3bf22b9088ab" + new Random().nextInt(10000))
	                .add("deviceid", "352284040670808")
	                .add("version", "1")
	                .add("channelid", "001")
	                .add("link_path", "100000_110003")
	                .add("clientid", "87")
	                .add("dd", "8692-A00")
	                .add("pwd", "69c8597d5e079efe633dcd30d5f6091110e005ca")
	                .add("token", "")
	                .add("isJailbreak", "1")
	                .add("bd", "a9xproltechn")
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; 8692-A00 Build/KOT49H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
					.addHeader("Host", "uapi.jiayuan.com")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("密码错误")) {
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
