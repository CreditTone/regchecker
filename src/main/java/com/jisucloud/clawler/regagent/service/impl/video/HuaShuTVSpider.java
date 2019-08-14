package com.jisucloud.clawler.regagent.service.impl.video;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "wasu.com", 
		message = "华数TV全网影视是三网融合平台下的综合视频网站。提供全网热门电影、电视剧,少儿动漫、综艺娱乐、求索纪录片、体育资讯、3D、VR、高清电视直播等在线视频点播直播。", 
		platform = "wasu", 
		platformName = "华数TV", 
		tags = { "影音", "视频", "MV" , "TV" }, 
		testTelephones = { "18611216720", "18210538513" })
public class HuaShuTVSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://uc.wasu.cn/member/index.php/Register/existPhone/plat/pc";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("key", account)
	                .add("name", "phone")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "uc.wasu.cn")
					.addHeader("Referer", "https://uc.wasu.cn/member/index.php/Register")
					.post(formBody)
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
