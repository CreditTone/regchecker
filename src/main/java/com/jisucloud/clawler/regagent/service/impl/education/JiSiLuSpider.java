package com.jisucloud.clawler.regagent.service.impl.education;

import com.deep007.spiderbase.okhttp.OKHttpUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "jisilu.com", 
		message = "集思录是一个低风险投资理财问答社区... 使用Chrome浏览本站最佳 | 声明:本站所有收费以及免费的信息和数据仅供参考。", 
		platform = "jisilu", 
		platformName = "集思录", 
		tags = { "理财资讯","学习","社区" }, 
		testTelephones = { "18230012895", "18212345678" })
public class JiSiLuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.jisilu.cn/?/account/ajax/check_mobile/mobile-" +account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "www.jisilu.cn")
					.addHeader("Referer", "https://www.jisilu.cn/m/register/")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被使用")) {
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
