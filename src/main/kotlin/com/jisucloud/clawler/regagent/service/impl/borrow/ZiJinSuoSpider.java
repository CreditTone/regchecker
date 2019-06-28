package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZiJinSuoSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "紫金所是手机理财应用软件，把守护资金出借人和小微企业的资金和数据安全作为企业发展的宗旨 紫金所存管模式采取了最被监管部门认可的银行直接存管模式，将为出借人和借款人分别开设独立的个人银行存管账户该平台。";
	}

	@Override
	public String platform() {
		return "zijinsuo";
	}

	@Override
	public String home() {
		return "zijinsuo.com";
	}

	@Override
	public String platformName() {
		return "紫金所";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.zijinsuo.com/register.do?action=restMobile&mobile=" + account + "&_=" +System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.zijinsuo.com")
					.addHeader("Referer", "https://www.zijinsuo.com")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("1");
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
