package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class XinYongBaoSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(20, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	
	@Override
	public String message() {
		return "中国个人信用风险创新平台-信用宝,为用户提供全方位出借、P2P网络借贷服务,专业的风险管理让出借、P2P网络借贷更加高效安全,您身边的个人金融出借专家。";
	}

	@Override
	public String platform() {
		return "xyb100";
	}

	@Override
	public String home() {
		return "xyb100.com";
	}

	@Override
	public String platformName() {
		return "信用宝金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13691032050", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phoneNumber", account)
	                .build();
			Request request = new Request.Builder().url("https://www.xyb100.com/regist/check_phone_new")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.xyb100.com")
					.addHeader("Referer", "https://www.xyb100.com/regist")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("false")) {
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
