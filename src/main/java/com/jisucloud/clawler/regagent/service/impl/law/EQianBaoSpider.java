package com.jisucloud.clawler.regagent.service.impl.law;

import com.deep007.spiderbase.okhttp.OKHttpUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "esign.com", 
		message = "e签宝(www.esign.cn)专注电子签名行业16年，致力于为客户提供从电子签名到文档管理，从签名数据存证到司法出证的完整合法的全闭环电子签名服务。", 
		platform = "esign", 
		platformName = "e签宝", 
		tags = { "电子合同" }, 
		testTelephones = { "18230012895", "18210538513" })
public class EQianBaoSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://tapi.esign.cn/account-webserver/login/commit/user";
			RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"), "{\"principal\":\""+account+"\",\"credentials\":\"b795accda6ff95bf7a1e01491f75f9f4\",\"loginParams\":{\"endpoint\":\"PC\"}}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "tapi.esign.cn")
					.addHeader("Referer", "https://user.esign.cn/login/web?localAccount=" + account )
					.post(requestBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("密码错误")) {
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
