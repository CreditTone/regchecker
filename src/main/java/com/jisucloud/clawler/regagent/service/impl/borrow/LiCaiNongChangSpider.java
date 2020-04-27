package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "lcfarm.com", 
		message = "布谷农场(原名理财农场)是由A股上市公司“诺普信”和国资机构“深创投”领投的深耕三农的互联网金融平台。已率先提交平台自查报告,中国互金协会首批会员。", 
		platform = "lcfarm", 
		platformName = "布谷农场", 
		tags = { "P2P", "农民贷" , "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class LiCaiNongChangSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.lcfarm.com/api/user/check.htm";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json;charset=UTF-8"),
							"{\"checkType\":\"PHONE\",\"bussinessType\":\"PBAND\",\"PHONE\":\""+account+"\"}");
			Request request = new Request.Builder().url(url)
					.addHeader("loginSource", "PC")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.lcfarm.com")
					.addHeader("Referer", "https://www.lcfarm.com/register.html?channel=pcgwzc2")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已注册")) {
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
