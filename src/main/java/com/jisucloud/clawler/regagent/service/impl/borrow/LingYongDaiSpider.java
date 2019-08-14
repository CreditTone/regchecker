package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import com.deep007.spiderbase.okhttp.OKHttpUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "lingyongdai.com", 
		message = "零用贷是重信金融旗下专注于互联网金融业务的信息中介平台,旨在为投资者提供低风险,高回报的理财产品,为有资金需求者提供低成本、高效的贷款服务。", 
		platform = "lingyongdai", 
		platformName = "lingyongdaiName", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" },
		excludeMsg = "倒闭")
public class LingYongDaiSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.lingyongdai.com/passport/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("password", "1asdnazp213nz")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.lingyongdai.com")
					.addHeader("Referer", "https://www.lingyongdai.com/passport/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("用户或密码不对")) {
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
