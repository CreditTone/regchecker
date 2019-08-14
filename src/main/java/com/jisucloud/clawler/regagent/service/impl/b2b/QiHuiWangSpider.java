package com.jisucloud.clawler.regagent.service.impl.b2b;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "qihuiwang.com", 
		message = "企汇网是中国企业信息化及B2B电子商务服务平台,汇集国内海量真实供求信息,涵盖原材料、工业品、消费品、商务服务和行业资讯等全方位的商业信息,找产品、找企业。", 
		platform = "qihuiwang", 
		platformName = "企汇网", 
		tags = { "b2b" ,"商机" ,"生意" }, 
		testTelephones = { "18210538513", "13925306966" })
public class QiHuiWangSpider extends PapaSpider {

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://u.qihuiwang.com/Member/RegMoblie?clientid=mb&BindMobile="+account+"&txtmobliecode=";
			FormBody formBody = new FormBody
	                .Builder()
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "u.qihuiwang.com")
					.addHeader("Referer", "http://u.qihuiwang.com/member/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("rue")) {
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
