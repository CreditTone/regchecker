package com.jisucloud.clawler.regagent.service.impl.game;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class _17173Spider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "17173是中国游戏第一门户站,全年365天保持不间断更新,您可以在这里获得专业的游戏新闻资讯,完善的游戏攻略专区,人气游戏论坛以及游戏测试账号等,是游戏玩家首选网络。";
	}

	@Override
	public String platform() {
		return "17173";
	}

	@Override
	public String home() {
		return "17173.com";
	}

	@Override
	public String platformName() {
		return "17173游戏";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18720982607", "18210538513");
	}


	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.17173.com/register/validate?field=mobile&value="+account+"&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.17173.com")
					.addHeader("Referer", "https://passport.17173.com/register")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已经注册")) {
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
