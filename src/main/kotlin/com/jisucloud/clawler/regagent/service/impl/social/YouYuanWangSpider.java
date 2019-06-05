package com.jisucloud.clawler.regagent.service.impl.social;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class YouYuanWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "有缘网是中国领先的大众婚恋交友移动互联网平台,专注于为最广泛的年轻单身男女婚恋交友创造更多机会和可能。找对象,上有缘网!";
	}

	@Override
	public String platform() {
		return "youyuan";
	}

	@Override
	public String home() {
		return "youyuan.com";
	}

	@Override
	public String platformName() {
		return "有缘网";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("房产", new String[] {  });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new YouYuanWangSpider().checkTelephone("18210538500"));
//		System.out.println(new YouYuanWangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://huluemt.zhuanjiaoapp.com/(unknown)/user/login.gy?oh=EA0FCB54ED390B576905B4BEFB822A75_1559723901031_31a61abae975e8f0";
			String postJson = "{\"did\":\"\",\"account\":\""+account+"\",\"platformInfo\":{\"fid\":\"47482\",\"platform\":\"3\",\"pid\":\"352284040670808\",\"mac\":\"08:6D:41:D5:7A:6A\",\"h\":800,\"version\":\"40060107\",\"w\":480,\"product\":\"1001\",\"phonetype\":\"a9xproltechn_MMB29M.A9100ZCU1AQB3\",\"appName\":\"yyw\",\"netType\":2,\"systemVersion\":\"4.4.2\",\"baseType\":\"yyw\",\"packName\":\"com.youyuan.yyhl\",\"release\":\"20190528\",\"verType\":5},\"password\":\"41234dasda\",\"loginType\":0,\"verType\":0}";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.2; 8692-A00 Build/KOT49H)")
					.addHeader("Host", "huluemt.zhuanjiaoapp.com")
					.post(FormBody.create(MediaType.get("application/json; charset=utf-8"), postJson))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String errorMsg = response.header("errorMsg");
			errorMsg = URLDecoder.decode(errorMsg);
			if (errorMsg.contains("不存在")) {
				return false;
			}
			if (errorMsg.contains("错误") || errorMsg.contains("不匹配") || errorMsg.contains("或")) {
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
