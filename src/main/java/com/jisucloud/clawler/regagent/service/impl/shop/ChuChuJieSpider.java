package com.jisucloud.clawler.regagent.service.impl.shop;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "ctfmall.com", 
		message = "楚楚街，移动端电子商务平台。业务类型有9块9包邮、品牌限时抢购等，主营类目涵盖男女服装、化妆品、母婴、零食等。", 
		platform = "ctfmall", 
		platformName = "楚楚街", 
		tags = { "购物" , "9.9包邮" }, 
		testTelephones = { "18779861101", "18212345678" })
public class ChuChuJieSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://api-passport.chuchujie.com/api.php";
			String json = "{\"channel\":\"QD_appstore\",\"package_name\":\"com.culiukeji.huanletao\",\"client_version\":\"3.9.101\",\"ageGroup\":\"AG_0to24\",\"client_type\":\"h5\",\"api_version\":\"v5\",\"imei\":129926157972059,\"method\":\"login_by_phone\",\"gender\":\"1\",\"token\":\"\",\"userId\":\"\",\"verify_code\":\"\",\"phone_number\":\""+account+"\",\"update_verify_flag\":false,\"password\":\"c142ba19c1d6285b1a4516c25f25aef0\",\"sms_code\":\"\"}";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("data", json)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "api-passport.chuchujie.com")
					.addHeader("Referer", "https://m.chuchujie.com/user/user_login.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("status\":11")) {
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
