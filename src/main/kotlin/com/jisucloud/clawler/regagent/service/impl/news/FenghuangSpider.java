package com.jisucloud.clawler.regagent.service.impl.news;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class FenghuangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "凤凰网是中国领先的综合门户网站,提供含文图音视频的全方位综合新闻资讯、深度访谈、观点评论、财经产品、互动应用、分享社区等服务,同时与凤凰无线。";
	}

	@Override
	public String platform() {
		return "ifeng";
	}

	@Override
	public String home() {
		return "ifeng.com";
	}

	@Override
	public String platformName() {
		return "凤凰网";
	}


	@Override
	public String[] tags() {
		return new String[] {"新闻资讯"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new FenghuangSpider().checkTelephone("18210538000"));
//		System.out.println(new FenghuangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://id.ifeng.com/api/checkMobile?callback=jQuery183019200945270426273_" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("u",account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "id.ifeng.com")
					.addHeader("Referer", "https://id.ifeng.com/user/register/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("\\u5df2\\u5b58\\u5728")) {
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
