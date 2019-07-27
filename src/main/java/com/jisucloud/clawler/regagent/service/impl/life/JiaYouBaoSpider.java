package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class JiaYouBaoSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "加油宝是一个聚焦大能源、大健康等安全领域,综合运用消费+金融+互联网手段,围绕以车主为代表的中产阶级人群的刚性消费场景,提供多赚好省更安全的创新产品平台。";
	}

	@Override
	public String platform() {
		return "jyblife";
	}

	@Override
	public String home() {
		return "jyblife.com";
	}

	@Override
	public String platformName() {
		return "加油宝";
	}

	@Override
	public String[] tags() {
		return new String[] {"汽车" , "中产阶级" , "石油"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18970010557", "18210538510");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://sweb.jyblife.com/base/index?t=" + System.currentTimeMillis();
			RequestBody formBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"), "{\"cmd\":\"42010103\",\"tel\":\""+account+"\",\"pwd\":\"a2737dcffa78b81441ca836003cd56d41ce7bcf1\",\"smscode\":\"\",\"user_type\":\"\"}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "sweb.jyblife.com")
					.addHeader("Referer", "https://www.jyblife.com/login.shtml?redirect=https%3A%2F%2Fwww.jyblife.com%2F")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("5000020010")) {
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
