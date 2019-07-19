package com.jisucloud.clawler.regagent.service.impl.work;

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
public class HaoFangTongSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "好房通｜房产中介系统行业标准引领者。好房通ERP——是国内先进的房产中介管理系统，集销售管理与OA办公于一体,是房产中介管理与营销不可或缺的办公系统房管软件，好房通房产中介管理软件让中介.";
	}

	@Override
	public String platform() {
		return "hftsoft";
	}

	@Override
	public String home() {
		return "hftsoft.com";
	}

	@Override
	public String platformName() {
		return "好房通";
	}

	@Override
	public String[] tags() {
		return new String[] {"OA系统", "办公软件"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13771025665", "18209649992");
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://www.hftsoft.com/Home/Register/checkPhone?action=checkPhone&mobile="+account+"&tmp=0.39787207731392094";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.hftsoft.com/user/register.shtml")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().contains("exist");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
