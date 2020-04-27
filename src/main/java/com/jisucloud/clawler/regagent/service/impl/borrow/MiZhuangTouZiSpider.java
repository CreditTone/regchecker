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
		home = "mizlicai.com", 
		message = "米庄理财正式成立,隶属于杭州信釜资产管理有限公司,是爱财集团下独立品牌。米庄理财曾获中国互联网金融行业百强企业,专注于小额分散微金融。", 
		platform = "mizlicai", 
		platformName = "米庄理财", 
		tags = { "P2P", "理财" , "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class MiZhuangTouZiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://api.mizlicai.com/register/checkRegisterable.json";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("os", "PC")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "api.mizlicai.com")
					.addHeader("Referer", "https://www.mizlicai.com/Regist")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已经被注册")) {
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
