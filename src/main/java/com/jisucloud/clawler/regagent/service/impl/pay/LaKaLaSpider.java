package com.jisucloud.clawler.regagent.service.impl.pay;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "lakala.com", 
		message = "拉卡拉是综合普惠科技金融平台。拉卡拉成立于2005年，秉承普惠、科技、创新、综合的理念，打造了底层统一，用户导向的共生系统，为个人和企业用户提供支付、征信、融资、社区金融等服务。", 
		platform = "lakala", 
		platformName = "拉卡拉", 
		tags = { "聚合支付", "科技金融" }, 
		testTelephones = { "13193091202", "13193091201" })
public class LaKaLaSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://mall.lakala.com/index.php/passport-signup_ajax_check_mobile.html";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("Referer", "https://mall.lakala.com/index.php/passport-signup.html")
					.post(createUrlEncodedForm("pam_account[login_name]=" + account))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已被注册")) {
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
