package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class HaiErSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "海尔顺逛商城(shunguang):网购空调、洗衣机、电视、热水器等大家电首选官方旗舰店;更有生活、厨房小家电、母婴家电、家庭医疗设备、智能产品等商品免费包邮。";
	}

	@Override
	public String platform() {
		return "shunguang";
	}

	@Override
	public String home() {
		return "shunguang.com";
	}

	@Override
	public String platformName() {
		return "海尔顺逛商城";
	}

	@Override
	public String[] tags() {
		return new String[] {"电商", "购物"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18515290717", "18210538513");
	}


	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://member.shunguang.com/checkUserNameExist.html?userName="+account+"&random=0.7428333015358664";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "member.shunguang.com")
					.addHeader("Referer", "http://member.shunguang.com/toRegister.html")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已存在")) {
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
