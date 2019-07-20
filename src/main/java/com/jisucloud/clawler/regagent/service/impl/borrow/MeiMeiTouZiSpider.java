package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

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
public class MeiMeiTouZiSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "美美投资是鹏润控股旗下理财平台,专业的风控系统全程提供支持,银行资金存管全程资金流转透明,为投资者提供全面安全的资金保障.不同期限产品自由选择,适合各种投资需求。";
	}

	@Override
	public String platform() {
		return "meme2c";
	}

	@Override
	public String home() {
		return "meme2c.com";
	}

	@Override
	public String platformName() {
		return "美美投资";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "理财" , "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.meme2c.com/findpswd/getMobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.meme2c.com/findpswd/loginPassword")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("false");
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

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15161500000");
	}

}
