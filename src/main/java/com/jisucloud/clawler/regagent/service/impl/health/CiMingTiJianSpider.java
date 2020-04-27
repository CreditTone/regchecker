package com.jisucloud.clawler.regagent.service.impl.health;


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
		home = "ciming.com", 
		message = "慈铭体检中心专业可靠、体检项目齐全，2010年被人民日报社授予“健康中国特别贡献大奖”。慈铭健康体检管理集团股份有限公司是国内知名的专业化健康体检连锁机构，规模大，覆盖范围广，体检数量多。", 
		platform = "ciming", 
		platformName = "慈铭体检", 
		tags = { "体检", "医疗" }, 
		testTelephones = { "13877117175", "18212345678" })
public class CiMingTiJianSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://book.ciming.com/userRegistPhoneCheck.html?loginPhone="+account;
			FormBody formBody = new FormBody
	                .Builder()
	                .add("loginPhone",account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "book.ciming.com")
					.addHeader("Referer", "http://book.ciming.com/registers.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
