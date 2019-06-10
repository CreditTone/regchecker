package com.jisucloud.clawler.regagent.service.impl.health;

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
public class CiMingTiJianSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "慈铭体检中心专业可靠、体检项目齐全，2010年被人民日报社授予“健康中国特别贡献大奖”。慈铭健康体检管理集团股份有限公司是国内知名的专业化健康体检连锁机构，规模大，覆盖范围广，体检数量多。";
	}

	@Override
	public String platform() {
		return "ciming";
	}

	@Override
	public String home() {
		return "ciming.com";
	}

	@Override
	public String platformName() {
		return "慈铭体检";
	}

	@Override
	public String[] tags() {
		return new String[] {"体检", "医疗"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new CiMingTiJianSpider().checkTelephone("18210538000"));
//		System.out.println(new CiMingTiJianSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://book.ciming.com/userRegistPhoneCheck.html?loginPhone="+account;
			FormBody formBody = new FormBody
	                .Builder()
	                .add("loginPhone",account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "book.ciming.com")
					.addHeader("Referer", "http://book.ciming.com/registers.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
