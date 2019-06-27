package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class CaoLiaoQrSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "草料二维码是国内专业的二维码服务提供商,提供二维码生成,美化,印制,管理,统计等服务,帮助企业通过二维码展示信息并采集线下数据,提升营销和管理效率。";
	}

	@Override
	public String platform() {
		return "cli";
	}

	@Override
	public String home() {
		return "cli.com";
	}

	@Override
	public String platformName() {
		return "草料二维码";
	}

	@Override
	public String[] tags() {
		return new String[] {"工具"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new BaiduSpider().checkTelephone("13925306966"));
//		System.out.println(new BaiduSpider().checkTelephone("13925306960"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "https://user.cli.im/join/check_account";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("tel", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "user.cli.im")
					.addHeader("Referer", "https://cli.im/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已注册")) {
				return true;
			}
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
