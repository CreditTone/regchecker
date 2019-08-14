package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "hhedai.com", 
		message = "黄河金融是于2014年3月24日上线，是浙江众联在线资产管理有限公司旗下的互联网金融信息服务平台，结合利用平台的特点，使得黄河金融具有“零风险”、“稳收益”、“高流动”特点，满.足了广.大个人用户的理财需求。", 
		platform = "hhedai", 
		platformName = "黄河金融", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class HuangHeJinRongSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.hhedai.com/?user&q=getpwd&type=getphonecode&https=false&phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.hhedai.com")
					.addHeader("Referer", "https://www.hhedai.com/secure/register.html")
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
