package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "phyt88.com", 
		message = "普汇云通汽车供应链金融平台(www.phyt88.com),是专注于汽车产业链金融领域的创新互联网投融资平台,拥有强力集团背景支持,普汇云通团队由专业的金融、互联网、汽车。", 
		platform = "phyt88", 
		platformName = "普汇云通", 
		tags = { "P2P", "消费分期" , "车贷" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class PuHuiYunTongSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.phyt88.com/v3/login/login.jso";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("password", "9fd21a1b199f00b3481284ded45506b5032cbef1c4a48bcb1f181dbe87a9b3d6c274674d44e62695eb658778585327e7c8b1515efc8e390c97fff4654ee5891eb8f93f062cc90cb72eb794cb51a9ba79d73bc09976569c0d25a9445190de4b80ee388d5a03a766faaac6033124a827c3f2660165c69d28453ec0aa20fd84554d")
	                .add("comfrom", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.phyt88.com")
					.addHeader("Referer", "https://www.phyt88.com/phyt/register.shtml")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码不正确")) {
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
