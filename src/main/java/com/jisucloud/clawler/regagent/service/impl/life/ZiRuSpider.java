package com.jisucloud.clawler.regagent.service.impl.life;

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

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZiRuSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "高品质租房品牌“自如”，旗下“自如友家”“自如寓”两大产品，数万间公寓。自如承诺：3天不满意全额退款！信仰生活的人，迟早与自如相遇！自如是最高品质的租房信息|价格|网站。";
	}

	@Override
	public String platform() {
		return "ziroom";
	}

	@Override
	public String home() {
		return "ziroom.com";
	}

	@Override
	public String platformName() {
		return "自如租房";
	}

	@Override
	public String[] tags() {
		return new String[] {"房产", "租房" , "租房中介"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13426300000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://passport.ziroom.com/account/register/verify-account.html";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.ziroom.com")
					.addHeader("Referer", "http://www.ziroom.com/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getJSONObject("resp").getBooleanValue("exist")) {
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
