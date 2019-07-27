package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZhenJinFuSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "真金服是真融宝平台旗下网络借贷信息中介平台。专注于运用大数据、人工智能等金融科技技术,为需要投资的用户以及有资金需求的个人/企业提供一个诚信、高效的交易平台。";
	}

	@Override
	public String platform() {
		return "zhenrongbaop2p";
	}

	@Override
	public String home() {
		return "zhenrongbaop2p.com";
	}

	@Override
	public String platformName() {
		return "真金服";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.zhenrongbaop2p.com/account/checksendregsmsvcode";
			RequestBody formBody = FormBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), "mobile="+account+"&d_id=885d521b416ce8b21578c12ac52b2b6d&d_screen=1920_1080&d_timez=8&d_sys=MacIntel&_access_token=&platform=pc");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.zhenrongbaop2p.com")
					.addHeader("Referer", "https://account.zhenrongbaop2p.com/account/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已被注册")) {
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
