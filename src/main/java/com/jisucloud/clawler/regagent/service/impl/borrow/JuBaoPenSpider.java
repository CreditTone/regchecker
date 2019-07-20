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
public class JuBaoPenSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "钜宝盆-致力于打造安全可靠的互联网金融第三方平台。为您提供稳健的收益,严格的贷款审核保障,完善的贷后管理和网站技术保障。";
	}

	@Override
	public String platform() {
		return "jpjbp";
	}

	@Override
	public String home() {
		return "jpjbp.com";
	}

	@Override
	public String platformName() {
		return "钜宝盆";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.jpjbp.com/check-cellphone?cellphone=" + account;
			FormBody formBody = new FormBody
	                .Builder()
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.jpjbp.com/sign-up")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("true")) {
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

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15161509916");
	}

}
