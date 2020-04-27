package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "zhubaodai.com", 
		message = "珠宝贷是国内专注珠宝行业的网贷、出借服务平台,是拥有上市公司及多家知名珠宝企业股东背景的网络信息中介机构。", 
		platform = "zhubaodai", 
		platformName = "珠宝贷", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class ZhuBaoDaiSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.zhubaodai.com/login/login/ajaxValidate.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("vCode", account)
	                .add("vType", "1")
	                .add("vCodeType", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.zhubaodai.com/login/login/register.do")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("USER_EXIST")) {
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
