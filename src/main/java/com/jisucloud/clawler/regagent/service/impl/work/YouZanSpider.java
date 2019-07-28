package com.jisucloud.clawler.regagent.service.impl.work;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

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
public class YouZanSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "有赞是一个商家服务公司，我们帮助每一位重视产品和服务的商家成功。目前旗下拥有：有赞微商城、有赞零售、有赞教育、有赞美业、有赞小程序等SaaS软件产品，适用全行业多场景，帮商家网上开店、网上营销、管理客户、获取订单。";
	}

	@Override
	public String platform() {
		return "youzan";
	}

	@Override
	public String home() {
		return "youzan.com";
	}

	@Override
	public String platformName() {
		return "有赞";
	}

	@Override
	public String[] tags() {
		return new String[] {"saas", "生意" , "电商"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "13761090875");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://uic.youzan.com/buyer/auth/authConfirm.json";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("code", "")
	                .add("source", "13")
	                .add("password", "")
	                .add("country_code", "+86")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "uic.youzan.com")
					.addHeader("Referer", "https://h5.youzan.com/wscuser/account/shop-registry#")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("请登录")) {
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
