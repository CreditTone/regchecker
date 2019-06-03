package com.jisucloud.clawler.regagent.service.impl.life;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class BaiduSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "全球最大的中文搜索引擎、致力于让网民更便捷地获取信息，找到所求。百度超过千亿的中文网页数据库，可以瞬间找到相关的搜索结果。";
	}

	@Override
	public String platform() {
		return "baidu";
	}

	@Override
	public String home() {
		return "baidu.com";
	}

	@Override
	public String platformName() {
		return "百度";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("生活", new String[] { "app市场" });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new BaiduSpider().checkTelephone("18210014444"));
//		System.out.println(new BaiduSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://passport.taihe.com/v2/api/checkphone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("login_id", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.taihe.com")
					.addHeader("Referer", "http://passport.taihe.com/v2/web/register.html?u=http%3A%2F%2Fmusic.taihe.com%2F%3Ffr%3Dhao123")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			JSONObject result = JSON.parseObject(response.body().string());
			JSONObject data = result.getJSONObject("data");
			if (data != null && data.getBooleanValue("baidu")) {
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
