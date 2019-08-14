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
		home = "touyouquan.com", 
		message = "投友圈是具有社交、交易功能的金融社区，于2014年10月15日正式上线，注册用户已超过10000人。投友圈是由从业于金融、互联网行业多年的精英团队创建，团队成员来自于百度、奇虎360等知名的互联网公司。投友圈本着分散投资，减小风险的理念。", 
		platform = "touyouquan", 
		platformName = "投友圈", 
		tags = { "P2P", "社交" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class TouYouQuanSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.touyouquan.com/jsp/user/userLogin.htm";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userName", account)
	                .add("passwd", "moaasd12bia12le")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.touyouquan.com")
					.addHeader("Referer", "https://www.touyouquan.com/html/login.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("loginError")) {
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
