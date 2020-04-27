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
		home = "xyb100.com", 
		message = "中国个人信用风险创新平台-信用宝,为用户提供全方位出借、P2P网络借贷服务,专业的风险管理让出借、P2P网络借贷更加高效安全,您身边的个人金融出借专家。", 
		platform = "xyb100", 
		platformName = "信用宝", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13691032050", "18212345678" })
public class XinYongBaoSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phoneNumber", account)
	                .build();
			Request request = new Request.Builder().url("https://www.xyb100.com/regist/check_phone_new")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.xyb100.com")
					.addHeader("Referer", "https://www.xyb100.com/regist")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("false")) {
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
