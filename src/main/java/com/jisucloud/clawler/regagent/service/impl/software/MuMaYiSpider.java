package com.jisucloud.clawler.regagent.service.impl.software;


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
		home = "mumayi.com", 
		message = "木蚂蚁安卓应用市场——行业领先的安卓应用平台,专业的Android软件下载提供商,为您选择最好玩的手机游戏,为您提供免费安卓手机软件。通过Android游戏软件下载使用热度。", 
		platform = "mumayi", 
		platformName = "木蚂蚁", 
		tags = { "工具" , "软件下载" }, 
		testTelephones = { "13925306966", "18212345678" })
public class MuMaYiSpider extends PapaSpider {


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://u.mumayi.com/?a=checkuserfield";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("field", "phone")
	                .add("value", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "u.mumayi.com")
					.addHeader("Referer", "http://u.mumayi.com/?a=register&rnd=5.495531130112629")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已经存在")) {
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
