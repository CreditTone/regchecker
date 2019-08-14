package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;



import java.util.HashMap;
import java.util.Map;


@PapaSpiderConfig(
		home = "duanrong.com", 
		message = "短融网是创新、透明的互联网借贷信息服务中介平台,深耕“三农”金融,专注于小额资产,为出借人提供省心投、月月盈、散标等出借产品。", 
		platform = "duanrong", 
		platformName = "短融网", 
		tags = { "P2P" , "借贷" }, 
		testTelephones = { "18210538577", "18210538513" })
@Slf4j
public class DuanRongSpider extends PapaSpider {
	
	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
		headers.put("Host", "m.duanrong.com");
		headers.put("Referer", "https://m.duanrong.com/memberLogin");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return Headers.of(headers);
	}


	public boolean checkTelephone(String account) {
		String url = "https://m.duanrong.com/isExist";
		try {
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobileNumber", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.headers(getHeader())
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("true")) {
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
