package com.jisucloud.clawler.regagent.service.impl.borrow;

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
		home = "manaowan.com", 
		message = "玛瑙湾根据国家监管和合规方向,撮合小额分散的资产,目前主要为车金融、消费金融及供应链金融,同时引入第三方担保,致力于更好地满足中小微企业和个人投融资需求。", 
		platform = "manaowan", 
		platformName = "玛瑙湾", 
		tags = { "P2P", "小微金融", "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class MaNaoWangSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.manaowan.com/Account/ajaxLoginOrRegister8717/"+account+"?_=" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("needSend", "0")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "m.manaowan.com")
					.addHeader("Referer", "https://m.manaowan.com/Account/show/entry")
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
