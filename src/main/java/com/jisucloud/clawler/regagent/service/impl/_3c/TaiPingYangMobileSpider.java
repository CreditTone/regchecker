package com.jisucloud.clawler.regagent.service.impl._3c;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class TaiPingYangMobileSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "太平洋电脑网是专业IT门户网站,为用户和经销商提供IT资讯和行情报价,涉及电脑,手机,数码产品,软件等。";
	}

	@Override
	public String platform() {
		return "pconline";
	}

	@Override
	public String home() {
		return "pconline.com";
	}

	@Override
	public String platformName() {
		return "太平洋电脑网";
	}

	@Override
	public String[] tags() {
		return new String[] {"3c", "科技" ,"智能手机"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15510257873", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport3.pconline.com.cn/passport3/api/validate_mobile.jsp?mobile="+account+"&req_enc=UTF-8";
			String postData = "{}:";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "passport3.pconline.com.cn")
					.addHeader("Referer", "https://my.pconline.com.cn/passport/mobileRegister.jsp")
					.post(FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), postData))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已经注册")) {
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
