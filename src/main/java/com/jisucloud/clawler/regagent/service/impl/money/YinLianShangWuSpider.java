package com.jisucloud.clawler.regagent.service.impl.money;


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
		home = "chinaums.com", 
		message = "银联商务股份有限公司（UMS），是中国银联旗下的、专门从事银行卡受理市场建设和提供综合支付服务的机构。", 
		platform = "chinaums", 
		platformName = "银联商务", 
		tags = { "理财", "信用卡" , "储蓄" }, 
		testTelephones = { "15985268900", "18212345678" })
public class YinLianShangWuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://ump.chinaums.com/ajax/checkMobile2";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "ump.chinaums.com")
					.addHeader("Referer", "https://ump.chinaums.com/v2/pages/perp/mobileRegist.jsp?id=10033&returnURL=https://www.chinaums.com/geongf/ump/login&redirect_uri=https://www.chinaums.com/geongf/ump/login&state=filter")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("%B2%E5%AD%98%E5%9C%A8")) {
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
