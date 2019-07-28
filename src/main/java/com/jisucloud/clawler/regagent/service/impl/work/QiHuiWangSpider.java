package com.jisucloud.clawler.regagent.service.impl.work;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class QiHuiWangSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "企汇网是中国企业信息化及B2B电子商务服务平台,汇集国内海量真实供求信息,涵盖原材料、工业品、消费品、商务服务和行业资讯等全方位的商业信息,找产品、找企业。";
	}

	@Override
	public String platform() {
		return "qihuiwang";
	}

	@Override
	public String home() {
		return "qihuiwang.com";
	}

	@Override
	public String platformName() {
		return "企汇网";
	}

	@Override
	public String[] tags() {
		return new String[] {"b2b" ,"商机" ,"生意"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "13925306966");
	}

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
