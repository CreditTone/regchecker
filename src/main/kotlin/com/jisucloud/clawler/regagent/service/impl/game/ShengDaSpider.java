package com.jisucloud.clawler.regagent.service.impl.game;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ShengDaSpider implements PapaSpider {

	private Session session = JJsoup.newSession();

	@Override
	public String message() {
		return "上海盛大网络发展有限公司，作为领先的互动娱乐媒体企业，盛大网络通过盛大游戏、盛大文学、盛大在线等主体和其它业务，向广大用户提供多元化的互动娱乐内容和服务。";
	}

	@Override
	public String platform() {
		return "sdo";
	}

	@Override
	public String home() {
		return "sdo.com";
	}

	@Override
	public String platformName() {
		return "盛大游戏";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ShengDaSpider().checkTelephone("18210530000"));
//		System.out.println(new ShengDaSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		
		for (int i = 0 ; i < 10 ; i++) {
			String url = "https://cas.sdo.com/authen/checkAccountType.jsonp?callback=checkAccountType_JSONPMethod&serviceUrl=register.sdo.com&appId=991002500&areaId=201000&authenSource=2&inputUserId="+account+"&locale=zh_CN&productId=1&productVersion=1.7&version=21&_=" + System.currentTimeMillis();
			try {
				Connection.Response response = session.connect(url)
						.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
						.header("Host", "cas.sdo.com")
						.header("Referer", "http://register.sdo.com/register/index?appId=991002500&areaId=201000")
						.execute();
				String res = response.body();
				if (res.contains("recommendLoginType\": 15")) {
					return true;
				}else if (res.contains("recommendLoginType")) {
					return false;
				}
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
