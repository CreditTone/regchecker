package com.jisucloud.clawler.regagent.service.impl.education;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZhongGuoZhiWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	private String name = null;

	@Override
	public String message() {
		return "中国知网，是国家知识基础设施的概念，由世界银行于1998年提出。CNKI工程是以实现全社会知识资源传播共享与增值利用为目标的信息化建设项目。";
	}

	@Override
	public String platform() {
		return "cnki";
	}

	@Override
	public String home() {
		return "cnki.net";
	}

	@Override
	public String platformName() {
		return "中国知网";
	}

	@Override
	public String[] tags() {
		return new String[] {"中国学术文献", "外文文献", "学位论文"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhongGuoZhiWangSpider().checkTelephone("13910252045"));
//		System.out.println(new ZhongGuoZhiWangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://my.cnki.net/mycnki/RealName/Server.aspx?mobile="+account+"&temp=741&operatetype=4";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "my.cnki.net/mycnki")
					.addHeader("Referer", "http://my.cnki.net/mycnki/RealName/FindPsd.aspx")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("1");
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
		if (name != null) {
			Map<String, String> fields = new HashMap<>();
			fields.put("name" , name);
			return fields;
		}
		return null;
	}

}
