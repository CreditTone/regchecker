package com.jisucloud.clawler.regagent.service.impl.social;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TianYaSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "天涯社区提供论坛、部落、博客、问答、文学、相册、个人空间等服务。拥有天涯杂谈、娱乐八卦、情感天地等人气栏目,以及关天茶舍、煮酒论史等高端人文论坛。";
	}

	@Override
	public String platform() {
		return "tianya";
	}

	@Override
	public String home() {
		return "tianya.com";
	}

	@Override
	public String platformName() {
		return "天涯社区";
	}

	@Override
	public String[] tags() {
		return new String[] {"论坛" , "社交" , "校园"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new TianYaSpider().checkTelephone("18210538000"));
//		System.out.println(new TianYaSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.tianya.cn/register/checkName.do?userName="+account+"&_r="+System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.tianya.cn")
					.addHeader("Referer", "https://passport.tianya.cn/register/default.jsp?fowardURL=")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("该用户名可用")) {
				return false;
			}else {
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
