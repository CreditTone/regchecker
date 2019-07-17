package com.jisucloud.clawler.regagent.service.impl.money;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

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
public class AllianzSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	
	@Override
	public String message() {
		return "中德安联人寿保险有限公司是德国安联保险集团(Allianz SE)与中国中信信托有限责任公司(CITIC Trust)共同合资组建的人寿保险公司，是中国第一家获准成立的中欧合资保险公司。业务范围覆盖人寿、养老、投资、教育、医疗、意外等各个领域，全方位地满足客户的需求。";
	}

	@Override
	public String platform() {
		return "allianz";
	}

	@Override
	public String home() {
		return "allianz.com";
	}

	@Override
	public String platformName() {
		return "安联人寿";
	}

	@Override
	public String[] tags() {
		return new String[] {"理财" , "保险" , "健康保险" , "医疗保险"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15901537458", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://sales.allianz.com.cn/emall/eservice/account/login.action?action=checkmoblenumber";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://sales.allianz.com.cn/emall/eservice/account/register.action?action=initNewRegisterView")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("手机号不可用")) {
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
