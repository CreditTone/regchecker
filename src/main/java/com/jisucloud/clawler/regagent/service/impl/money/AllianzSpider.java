package com.jisucloud.clawler.regagent.service.impl.money;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "allianz.com", 
		message = "中德安联人寿保险有限公司是德国安联保险集团(Allianz SE)与中国中信信托有限责任公司(CITIC Trust)共同合资组建的人寿保险公司，是中国第一家获准成立的中欧合资保险公司。业务范围覆盖人寿、养老、投资、教育、医疗、意外等各个领域，全方位地满足客户的需求。", 
		platform = "allianz", 
		platformName = "中德安联", 
		tags = { "理财" , "保险" , "健康保险" , "医疗保险" }, 
		testTelephones = { "15901537458", "18210538513" })
public class AllianzSpider extends PapaSpider {

	
	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://sales.allianz.com.cn/emall/eservice/account/login.action?action=checkmoblenumber";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://sales.allianz.com.cn/emall/eservice/account/register.action?action=initNewRegisterView")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("手机号不可用")) {
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
