package com.jisucloud.clawler.regagent.service.impl.news;

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
public class QuTouTiaoSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "趣头条作为一款新生代内容资讯APP，由上海基分文化传播有限公司开发。团队致力于让用户的阅读更有价值，通过大数据算法和云计算等技术，为用户提供感兴趣、有价值的个性化内容及服务。2018年8月18日，趣头条提交美国IPO申请。";
	}

	@Override
	public String platform() {
		return "qutoutiao";
	}

	@Override
	public String home() {
		return "qutoutiao.com";
	}

	@Override
	public String platformName() {
		return "趣头条";
	}

	@Override
	public String[] tags() {
		return new String[] {"新闻资讯"};
	}

	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18720982007", "18210538513");
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://qac-qupost.qutoutiao.net/member/checkPhoneExistOrNot?telephone="+account+"&dtu=200";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("name",account)
	                .add("format", "json")
	                .add("from", "mobile")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "qac-qupost.qutoutiao.net")
					.addHeader("Referer", "https://mp.qutoutiao.net/register/step-one")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("is_exist\":1")) {
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
