package com.jisucloud.clawler.regagent.service.impl.education;

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
public class ShangDeSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "尚德机构（NYSE:STG），专注于学历教育、职业教育的互联网教育公司。尚德机构的培训课程和服务范围广阔，提供全国各省市的精品直播课程、录播课程等，业务涵盖上班族学历 MBA 教师资格证 会计职称 国际MBA 等高等教育、职业资格认证以及与职业相关的就业服务。";
	}

	@Override
	public String platform() {
		return "sunlands";
	}

	@Override
	public String home() {
		return "sunlands.com";
	}

	@Override
	public String platformName() {
		return "尚德教育";
	}

	@Override
	public String[] tags() {
		return new String[] {"学历提升","自考","教育"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18779861101", "18210538513");
	}


	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://passport.sunlands.com/existsUser.action";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.sunlands.com")
					.addHeader("Referer", "http://passport.sunlands.com/login?service=http%3A%2F%2Fyi.sunlands.com%2Fent-portal-war%2Fnew_pt_uc%2Fmy_lesson%2FlessonHome.action%3Fauth%3Dtrue")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			System.out.println(res);
			if (res.contains("true")) {
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
