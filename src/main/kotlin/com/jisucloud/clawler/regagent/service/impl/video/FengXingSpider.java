package com.jisucloud.clawler.regagent.service.impl.video;

import com.jisucloud.clawler.regagent.service.PapaSpider;
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
@Component
public class FengXingSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "风行视频网,提供免费电影、电视剧、综艺、动漫、体育等视频内容的在线观看和下载.累积7亿用户的全平台,为传媒机构和品牌客户开设了官方视频服务账号。";
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
		return "风行视频";
	}

	@Override
	public String[] tags() {
		return new String[] {"影音", "视频"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new FengXingSpider().checkTelephone("18210538513"));
//		System.out.println(new FengXingSpider().checkTelephone("13925306960"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://api.fun.tv/ajax/check_account/?isajax=1&dtime=" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "api.fun.tv")
					.addHeader("Referer", "http://www.fun.tv/account/reg")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已经注册")) {
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
