package com.jisucloud.clawler.regagent.service.impl.util;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "tmkoo.com", 
		message = "标库网是免费的商标查询、商标注册、商标服务网站,我们拥有中国最全的商标信息,数据实时跟商标局保持同步。商标申请人可以在申请商标前做商标注册查询。", 
		platform = "tmkoo", 
		platformName = "标库网", 
		tags = { "商标查询" , "工具" }, 
		testTelephones = { "18210538513", "18230012895" })
public class ZhongGuoShangBiaoWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.tmkoo.com/regp!ajaxCheckPhone.php?phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.tmkoo.com")
					.addHeader("Referer", "http://www.tmkoo.com/regp.php")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("false")) {
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
