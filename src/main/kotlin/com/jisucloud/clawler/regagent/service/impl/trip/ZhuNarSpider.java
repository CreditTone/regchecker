package com.jisucloud.clawler.regagent.service.impl.trip;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ZhuNarSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	private String name = null;

	@Override
	public String message() {
		return "神州租车 (CAR Inc.)成立于2007年9月，总部位于中国北京。作为中国汽车租赁行业的领跑者，神州租车积极借鉴国际成熟市场成功的汽车租赁模式，并结合中国客户的消费习惯，为广大消费者提供短租、长租及融资租赁等专业化的汽车租赁服务。";
	}

	@Override
	public String platform() {
		return "zuche";
	}

	@Override
	public String home() {
		return "zuche.com";
	}

	@Override
	public String platformName() {
		return "神州租车";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("媒体", new String[] { });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhuNarSpider().checkTelephone("13910252045"));
//		System.out.println(new ZhuNarSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.zhuna.cn/account/isreg/?mobile="+ account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.zhuna.cn")
					.addHeader("Referer", "http://www.zhuna.cn/account/register/")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			Object data = result.get("data");
			if (data instanceof JSONArray) {
				JSONArray data2 = (JSONArray) data;
				JSONObject item = data2.getJSONObject(0);
				name = item.getString("NickName");
				name = StringUtil.unicodeToString(name);
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
		if (name != null) {
			Map<String, String> fields = new HashMap<>();
			fields.put("name" , name);
			return fields;
		}
		return null;
	}

}
