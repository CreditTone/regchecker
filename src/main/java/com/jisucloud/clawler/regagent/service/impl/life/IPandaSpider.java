package com.jisucloud.clawler.regagent.service.impl.life;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "ipanda.com", 
		message = "iPanda熊猫频道面向全球网民打造极具吸引力的熊猫主题新媒体集群，通过熊猫向世界传播和平、友爱理念，提升国家形象。网站对大熊猫繁育、日常起居、娱乐的情况进行全天候24小时近距离视频直播及点。", 
		platform = "ipanda", 
		platformName = "iPanda熊猫频道", 
		tags = { "和平", "媒体" , "熊猫" }, 
		testTelephones = { "13477079270", "18210538513" })
public class IPandaSpider extends PapaSpider {


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "https://reg.cntv.cn/login/login.action?callback=jsonp1564309036007&version=2.0&username="+account+"&password=dasdasdasd&service=client_transaction&from=http://www.ipanda.com&remuser=";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.ipanda.com/")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().contains("密码错误");
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
