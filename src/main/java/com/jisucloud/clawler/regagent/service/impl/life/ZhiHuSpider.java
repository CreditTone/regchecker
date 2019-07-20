package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZhiHuSpider extends PapaSpider {
	
	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "有问题,上知乎。知乎,可信赖的问答社区,以让每个人高效获得可信赖的解答为使命。知乎凭借认真、专业和友善的社区氛围,结构化、易获得的优质内容,基于问答的内容生产。";
	}

	@Override
	public String platform() {
		return "zhihu";
	}

	@Override
	public String home() {
		return "zhihu.com";
	}

	@Override
	public String platformName() {
		return "知乎";
	}

	@Override
	public String[] tags() {
		return new String[] {"社区", "知识"};
	}
	
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13210538513", "18210538513");
	}

	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String postData = "phone_no=%2B86"+account;
			String url = "https://api.zhihu.com/validate/register_form";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Futureve/4.16.0 Mozilla/5.0 (Linux; Android 4.4.2; 8692-A00 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36 Google-HTTP-Java-Client/1.22.0 (gzip)")
					.addHeader("Host", "api.zhihu.com")
					.addHeader("x-api-version", "3.0.51")
					.addHeader("x-app-version", "6.3.0")
					.addHeader("x-app-za", "OS=Android&Release=4.4.2&Model=8692-A00&VersionName=4.16.0&VersionCode=464&Width=480&Height=800&Installer=%E8%B1%8C%E8%B1%86%E8%8D%9A")
					.addHeader("x-udid", "AHBvXQa4rQ9LBTr7Vyfhl81k0Si7WQHTwlk=")
					.addHeader("x-app-local-unique-id", "1052694")
					.post(FormBody.create(MediaType.get("application/x-www-form-urlencoded; charset=UTF-8"), postData))
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = response.body().string();
			if (res.contains("已注册")) {
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
