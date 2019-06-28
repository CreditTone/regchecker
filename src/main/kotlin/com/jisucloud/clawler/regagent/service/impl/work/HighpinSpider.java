package com.jisucloud.clawler.regagent.service.impl.work;

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
public class HighpinSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "智联卓聘highpin.cn是智联招聘网旗下中高端人才招聘网站,整合全球1088个国家和地区40多个行业海量年薪10万以上的中高端职位。中高端人才求职、找工作,首选智联卓聘。";
	}

	@Override
	public String platform() {
		return "highpin";
	}

	@Override
	public String home() {
		return "highpin.com";
	}

	@Override
	public String platformName() {
		return "智联卓聘";
	}

	@Override
	public String[] tags() {
		return new String[] {"求职" , "招聘"};
	}

	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15700102866", "15700102860");
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://c.highpin.cn/Users/CheckUserName/?x-zp-client-id=d1c8cf49-2e75-4ace-dcd3-c85fcfb7998a";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "c.highpin.cn")
					.addHeader("Referer", "https://c.highpin.cn/Users/Register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("\"OtherMessage\":\"true")) {
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
