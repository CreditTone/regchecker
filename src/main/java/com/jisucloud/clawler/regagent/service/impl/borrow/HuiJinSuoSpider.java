package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class HuiJinSuoSpider extends PapaSpider {

	
	
	@Override
	public String message() {
		return "惠金所Hfax(北京中关村融汇金融信息服务有限公司)成立于2015年4月，是阳光保险旗下的互联网金融信息服务平台。惠金所定位于做有影响力的个人财富管理及中小微企业融资服务。";
	}

	@Override
	public String platform() {
		return "hfax";
	}

	@Override
	public String home() {
		return "hfax.com";
	}

	@Override
	public String platformName() {
		return "惠金所";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷" , "小微金融"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.hfax.com/pc-api/user/login";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json"), "{\"username\":\""+account+"\",\"password\":\"05b30f5bae2b85275f59a1f0195d1f83\"}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.hfax.com")
					.addHeader("Referer", "https://www.hfax.com/login.html#/?rsrc=https%3A%2F%2Fwww.hfax.com%2Fregister.html%23%2Fregister%3F")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("remainTimes") || res.contains("账户已冻结")) {
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
