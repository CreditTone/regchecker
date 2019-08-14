package com.jisucloud.clawler.regagent.service.impl.saas;


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
		home = "dingdingdan.com", 
		message = "订订单网是专业的纺织服装加工好订单交易平台,聚集了众多优质纺织服装加工厂,包括服装服饰、家纺、面辅料、工艺、设备等。为广州、深圳、东莞、虎门、上海、杭州等。", 
		platform = "dingdingdan", 
		platformName = "订订单", 
		tags = { "b2b" ,"商机" ,"生意" }, 
		testTelephones = { "13925306966", "18210538513" },
		exclude = true)
public class DingDingDanSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.dingdingdan.com/index.php?m=Home&c=Members&a=ajax_check";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("param", account)
	                .add("type", "mobile")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.dingdingdan.com")
					.addHeader("Referer", "https://www.dingdingdan.com/members/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("已经注册")) {
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
