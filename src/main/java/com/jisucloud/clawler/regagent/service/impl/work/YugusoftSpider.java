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
public class YugusoftSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "鱼骨让项目管理更轻松,以任务为基础的目标管理平台,帮助您更好管理企业的人、事和目标,Windows、Mac、Android、iPhone、微信等多终端提升企业执行力。";
	}

	@Override
	public String platform() {
		return "yugusoft";
	}

	@Override
	public String home() {
		return "yugusoft.com";
	}

	@Override
	public String platformName() {
		return "鱼骨";
	}

	@Override
	public String[] tags() {
		return new String[] {"办公软件" , "沟通平台"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513","18515290000");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://app01.yugusoft.com/ftask/proxy/v2/user/login.json?plat=web&build=999999";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("identify", account)
	                .add("password", "na13210czme")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "app01.yugusoft.com")
					.addHeader("Referer", "https://app01.yugusoft.com/ftask/web/index.html#/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			return res.contains("密码错误") || res.contains("验证码有误");
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
