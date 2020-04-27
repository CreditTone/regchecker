package com.jisucloud.clawler.regagent.service.impl.knowledge;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "zhihu.com", 
		message = "有问题,上知乎。知乎,可信赖的问答社区,以让每个人高效获得可信赖的解答为使命。知乎凭借认真、专业和友善的社区氛围,结构化、易获得的优质内容,基于问答的内容生产。", 
		platform = "zhihu", 
		platformName = "知乎",
		userActiveness = 0.6f,
		tags = { "社区", "知识" }, 
		testTelephones = { "13210538513", "18212345678" })
public class ZhiHuSpider extends PapaSpider {
	
	

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
