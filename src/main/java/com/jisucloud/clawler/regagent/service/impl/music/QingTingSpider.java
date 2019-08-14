package com.jisucloud.clawler.regagent.service.impl.music;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "qingting.fm", 
		message = "蜻蜓FM,不仅囊括国内外数千家网络电台和全国广播电台,还涵盖有声小说、儿童故事、相声、评书、戏曲、在线音乐、脱口秀、鬼故事、情感故事、财经、新闻、历史、健康。", 
		platform = "qingting", 
		platformName = "蜻蜓FM", 
		tags = { "听书", "生活休闲" , "FM" }, 
		testTelephones = { "18720982607", "18210538513" })
public class QingTingSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://u2.qingting.fm/u2/api/v4/check_phone_exist?phone_number="+account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "u2.qingting.fm")
					.addHeader("Referer", "https://www.qingting.fm/channels/217920/")
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
