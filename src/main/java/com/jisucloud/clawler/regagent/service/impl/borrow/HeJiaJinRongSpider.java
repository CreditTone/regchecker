package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "hejiajinrong.com", 
		message = "合家金融(www.hejiajinrong.com)隶属于杭州合家互联网金融服务有限公司(简称合家金融),是专注于以银行不良资产为核心的特色互联网金融平台。", 
		platform = "hejiajinrong", 
		platformName = "合家金融", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class HeJiaJinRongSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.hejiajinrong.com/api/user/validateMobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("os", "PC")
	                .add("version", "3.62")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.hejiajinrong.com/index/login?redirectUrl=%2Fuser%2Faccount")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return result.getJSONObject("validateMobile").getBooleanValue("validateMobile");
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
