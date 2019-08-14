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
		home = "cli.com", 
		message = "风行视频网,提供免费电影、电视剧、综艺、动漫、体育等视频内容的在线观看和下载.累积7亿用户的全平台,为传媒机构和品牌客户开设了官方视频服务账号。", 
		platform = "cli", 
		platformName = "风行视频网", 
		tags = { "影音", "视频" }, 
		testTelephones = { "15700102860", "18210538513" })
public class FengXingSpider extends PapaSpider {

	


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://api.fun.tv/ajax/check_account/?isajax=1&dtime=" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "api.fun.tv")
					.addHeader("Referer", "http://www.fun.tv/account/reg")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已经注册")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
