package com.jisucloud.clawler.regagent.service.impl.video;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "baofeng.com", 
		message = "暴风影音致力打造大型互联网视频播放平台,集在线视频和本地播放服务于一体,是专注提供免费、高清网络视频服务的大型视频网站。", 
		platform = "baofeng", 
		platformName = "暴风影音", 
		tags = { "影音", "视频", "MV" }, 
		testTelephones = { "15700102865", "18210538513" })
public class BaoFengYingYinSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://sso.baofeng.com/new/api/is_mobile_used?appid=8637&sign=de75e7a58d2fe0fb26bed9f2909d52595cf8ee90&mobile="+account+"&callback=jQuery112402612518137038946_"+System.currentTimeMillis()+"&_=" +System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "sso.baofeng.com")
					.addHeader("Referer", "http://www.baofeng.com/")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("is_used\":1") || res.contains("is_used\": 1")) {
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
