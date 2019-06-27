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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class MtimeSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "Mtime时光网,中国最专业的电影电视剧及影人资料库,这里有最新最专业的电影新闻,预告片,海报,写真和热门影评,同时提供电影院影讯查询,博客,相册和群组等服务,是电影粉丝的最佳电影社区。";
	}

	@Override
	public String platform() {
		return "mtime";
	}

	@Override
	public String home() {
		return "mtime.com";
	}

	@Override
	public String platformName() {
		return "Mtime时光网";
	}

	@Override
	public String[] tags() {
		return new String[] {"娱乐咨询", "追星"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new MtimeSpider().checkTelephone("13910252045"));
//		System.out.println(new MtimeSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.mtime.com/api/check_unique_loginname?loginName="+account+"&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.mtime.com")
					.addHeader("Referer", "https://passport.mtime.com/member/signin/?redirectUrl=http%3A%2F%2Fwww.mtime.com%2F")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("exist\":true")) {
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
