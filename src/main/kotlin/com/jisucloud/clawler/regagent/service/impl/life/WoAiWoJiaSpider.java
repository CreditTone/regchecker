package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WoAiWoJiaSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "我爱我家网为您提供二手房买卖、普通租赁、相寓品牌-住宅资质管理、新房交易、海外房产等房地产综合服务。您可以通过小区找房、地图找房、建立选房卡、联系经纪人、下载APP等方式自由选择心仪房源。";
	}

	@Override
	public String platform() {
		return "5i5j";
	}

	@Override
	public String home() {
		return "5i5j.com";
	}

	@Override
	public String platformName() {
		return "我爱我家网";
	}

	@Override
	public String[] tags() {
		return new String[] {"房产家居" , "房产中介"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new WoAiWoJiaSpider().checkTelephone("18515290717"));
//		System.out.println(new WoAiWoJiaSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.5i5j.com/passport/sigin?city=bj";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("password", "json21dsacvew")
	                .add("aim", "pc")
	                .add("service", "https://bj.5i5j.com/reglogin/index?preUrl=https%3A%2F%2Fbj.5i5j.com%2F")
	                .add("status", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.5i5j.com")
					.addHeader("Referer", "https://passport.5i5j.com/passport/sigin?city=bj")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			Document doc = Jsoup.parse(response.body().string());
			if (doc.select("#erromsg").text().contains("密码错误")) {
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
