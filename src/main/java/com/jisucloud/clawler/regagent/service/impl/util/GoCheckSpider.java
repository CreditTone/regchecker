package com.jisucloud.clawler.regagent.service.impl.util;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "gocheck.com", 
		message = "Gocheck论文检测系统,采用国际领先的海量论文动态语义跨域识别加指纹比对技术,通过运用最新的云检测服务部署使其能够快捷、稳定、准确地检测到文章中存在的抄袭和不当。", 
		platform = "gocheck", 
		platformName = "Gocheck", 
		tags = { "考试","论文","教育" }, 
		testTelephones = { "15210234070", "18212345678" })
public class GoCheckSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.gocheck.cn/loginAction/checkIsExist.do?checkName=" + account;
			FormBody formBody = new FormBody
	                .Builder()
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "www.gocheck.cn")
					.addHeader("Referer", "http://www.gocheck.cn/page/register.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
