package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class SouFangWangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "搜房房地产网是中国最大的房地产家居网络平台，提供全面及时的房地产新闻资讯内容，为所有楼盘提供网上浏览、业主论坛和社区网站，房地产精英人物个人主页，是国内房地产媒体及业内外网友公认的全球最大的房地产网络平台，搜房引擎给网友提供房地产网站中速度快捷内容全面的智能搜索。";
	}

	@Override
	public String platform() {
		return "fang";
	}

	@Override
	public String home() {
		return "fang.com";
	}

	@Override
	public String platformName() {
		return "搜房网";
	}

	@Override
	public String[] tags() {
		return new String[] {"房产家居"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15970663703", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.fang.com/login.api";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("uid", account)
	                .add("pwd", "3306d624b95730933d0fa5a74c6133142b08f8fe7bbcd9e5ef5750f5540c689792eae4d26888c9efd0451ec4db4e852756dbd113c5a370d446cd0fa728413b3097345787f093b46e41f51aec0d913f461a931d0d8bf1b580c293e25970b8f4195fee2f024c3313b730238bb989abd69610e4df1a53125ab9762ca9781375012a")
	                .add("Service", "soufun-passport-web")
	                .add("AutoLogin", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.fang.com")
					.addHeader("Referer", "https://passport.fang.com/?backurl=https://www.fang.com/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码错误")) {
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
