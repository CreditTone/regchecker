package com.jisucloud.clawler.regagent.service.impl.education;

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
public class XinDongFangZaiXianSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "新东方在线是新东方教育科技集团(NYSE:EDU)旗下专业的在线教育网站，其课程服务涵盖考研、托福、雅思、中学、外教口语、四六级、新概念、小语种等类别。致力于为广大用户提供个性化、互动化、智能化的在线学习体验。";
	}

	@Override
	public String platform() {
		return "koolearn";
	}

	@Override
	public String home() {
		return "koolearn.com";
	}

	@Override
	public String platformName() {
		return "新东方在线";
	}

	@Override
	public String[] tags() {
		return new String[] {"考试","学习","英语"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new XinDongFangZaiXianSpider().checkTelephone("15010645316"));
//		System.out.println(new XinDongFangZaiXianSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://login.koolearn.com/sso/mobileExists.do?callback=jQuery111207884664630899062_"+System.currentTimeMillis()+"&type=jsonp&mobile="+account+"&country=CN&countryCode=86&_="+System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "login.koolearn.com")
					.addHeader("Referer", "https://www.koolearn.com/?a_id=ff808081596ddecd01596ddecdff0000&kid=2d4bfdcf308a438eba4b8c9753ba9084&utm_source=PC%E5%93%81%E4%B8%93&utm_medium=pcpz&utm_campaign=%E7%99%BE%E5%BA%A6PC%E5%93%81%E4%B8%93&utm_content=%E6%A0%87%E9%A2%98&utm_term=1&ctx=&basePath=http%3A%2F%2Fun.koolearn.com%3A80%2F")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("true\"")) {
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
