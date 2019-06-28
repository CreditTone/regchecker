package com.jisucloud.clawler.regagent.service.impl.money;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class YinLianShangWuSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "银联商务股份有限公司（UMS），是中国银联旗下的、专门从事银行卡受理市场建设和提供综合支付服务的机构。";
	}

	@Override
	public String platform() {
		return "chinaums";
	}

	@Override
	public String home() {
		return "chinaums.com";
	}

	@Override
	public String platformName() {
		return "银联商务";
	}

	@Override
	public String[] tags() {
		return new String[] {"理财", "信用卡" , "储蓄"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268900", "18210538513");
	}

	@Override
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
