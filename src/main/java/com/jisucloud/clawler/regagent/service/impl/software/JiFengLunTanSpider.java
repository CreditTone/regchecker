package com.jisucloud.clawler.regagent.service.impl.software;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "gfan.com", 
		message = "机锋论坛是全球最大、用户量最多、资源最全面的中文安卓论坛,拥有海量的ROM、游戏、软件资源,同时为玩家们提 供最新最热的安卓手机资源,各种刷机教程。搞机就上机锋网|GFAN.COM。", 
		platform = "gfan", 
		platformName = "机锋论坛", 
		tags = { "3c", "科技" ,"智能手机" }, 
		testTelephones = { "15510257873", "18210538513" })
public class JiFengLunTanSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://my.gfan.com/isthisPhone";
			String postData = "cellphone="+account+"&editFlag=0&cellphoneFlag=1";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "http://my.gfan.com/login")
					.post(FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), postData))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已被注册")) {
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
