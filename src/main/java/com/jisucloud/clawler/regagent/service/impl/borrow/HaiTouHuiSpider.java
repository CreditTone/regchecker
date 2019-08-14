package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "htouhui.com", 
		message = "海投汇是由上市公司冠城大通出资设立的互联网金融平台，为投资人和借款人打造一个实现借贷关系的信息服务居间平台。", 
		platform = "htouhui", 
		platformName = "海投汇", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class HaiTouHuiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.htouhui.com/api/leo-core/user/mobile_can_regist?mobile=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.htouhui.com")
					.addHeader("Referer", "https://www.htouhui.com/#/register")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return result.getIntValue("status") == 920;
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
