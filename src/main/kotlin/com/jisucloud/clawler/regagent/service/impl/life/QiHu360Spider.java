package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class QiHu360Spider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "奇虎360是（北京奇虎科技有限公司）的简称，由周鸿祎于2005年9月创立，主营360杀毒为代表的免费网络安全平台和拥有问答等独立业务的公司。";
	}

	@Override
	public String platform() {
		return "360";
	}

	@Override
	public String home() {
		return "360.cn";
	}

	@Override
	public String platformName() {
		return "360";
	}

	@Override
	public String[] tags() {
		return new String[] {"系统工具", "游览器"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new QiHu360Spider().checkTelephone("18210014444"));
//		System.out.println(new QiHu360Spider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "https://login.360.cn/?callback=jQuery18309111090361054407_"+System.currentTimeMillis()+"&src=pcw_so&from=pcw_so&charset=UTF-8&requestScema=https&quc_sdk_version=6.7.0&quc_sdk_name=jssdk&o=User&m=checkmobile&mobile="+account+"&type=&_=" +System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "login.360.cn")
					.addHeader("Referer", "https://www.so.com/?src=hao")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = response.body().string();
			if (res.contains("\\u624b\\u673a\\u53f7\\u5df2\\u88ab\\u4f7f\\u7528") || res.contains("手机号已被使用")) {
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
