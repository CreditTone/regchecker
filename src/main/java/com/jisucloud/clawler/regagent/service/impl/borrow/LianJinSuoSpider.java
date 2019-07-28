package com.jisucloud.clawler.regagent.service.impl.borrow;

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
public class LianJinSuoSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "联金所是一家5年稳健运营三方投资的互联网金融平台,银行资金存管,100元起投,年化收益8%-14%,注册即送668元新手红包。";
	}

	@Override
	public String platform() {
		return "ufclub";
	}

	@Override
	public String home() {
		return "uf-club.com";
	}

	@Override
	public String platformName() {
		return "联金所";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.uf-club.com/ajaxCheckRegister.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("paramMap.cellPhone", account)
	                .add("paramMap.userName", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.uf-club.com/regInitZhuce.do")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("3")) {
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
