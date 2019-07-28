package com.jisucloud.clawler.regagent.service.impl.health;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class YuErWangSpider extends PapaSpider {

	


	@Override
	public String message() {
		return "育儿网为父母提供怀孕分娩,胎教,育儿,保健,喂养,常见病护理,早教知识.大容量的宝宝主页,育儿博客服务.还提供有声读物,儿歌,亲子游戏下载.为家长们提供了优秀全面的。";
	}

	@Override
	public String platform() {
		return "ci123";
	}

	@Override
	public String home() {
		return "ci123.com";
	}

	@Override
	public String platformName() {
		return "育儿网";
	}

	@Override
	public String[] tags() {
		return new String[] {"育儿", "保健"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18515290717", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://user.ci123.com/api/Reg/checkfield";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("field", "mobile")
	                .add("value", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "user.ci123.com")
					.addHeader("Referer", "http://user.ci123.com/account/NewAccount/?back_url=http://www.ci123.com/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("status").equals("2")) {
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
