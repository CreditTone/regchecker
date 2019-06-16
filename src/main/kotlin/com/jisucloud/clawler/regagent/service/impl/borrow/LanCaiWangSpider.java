package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LanCaiWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "懒财网成立于2013年11月，2014年5月正式上线运营，由北京懒财信息科技有限公司负责运营，网站于2014年5月正式上线。懒财网拥有一支有丰富互联网和金融经验的精英团队，分别来自于搜狗、百度、阿里等大型互联网科技公司。";
	}

	@Override
	public String platform() {
		return "lancai";
	}

	@Override
	public String home() {
		return "lancai.com";
	}

	@Override
	public String platformName() {
		return "懒财网";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new LanCaiWangSpider().checkTelephone("15985268904"));
//		System.out.println(new LanCaiWangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://api-main-backup.lancai.cn/sys_check_mobile.php";
			RequestBody formBody = FormBody.create(MediaType.get("application/json"), "{\"mobile\":"+account+"}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "api-main-backup.lancai.cn")
					.addHeader("Referer", "https://m.lancai.cn/welcome.html?from=/user/user_info.html")
					.addHeader("Origin", "https://m.lancai.cn")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return result.getJSONObject("data").getBooleanValue("registered");
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
