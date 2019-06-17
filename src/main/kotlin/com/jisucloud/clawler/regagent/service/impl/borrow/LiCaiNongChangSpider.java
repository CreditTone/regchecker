package com.jisucloud.clawler.regagent.service.impl.borrow;

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
public class LiCaiNongChangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "布谷农场(原名理财农场)是由A股上市公司“诺普信”和国资机构“深创投”领投的深耕三农的互联网金融平台。已率先提交平台自查报告,中国互金协会首批会员。";
	}

	@Override
	public String platform() {
		return "lcfarm";
	}

	@Override
	public String home() {
		return "lcfarm.com";
	}

	@Override
	public String platformName() {
		return "布谷农场";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "农民贷" , "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new LiCaiNongChangSpider().checkTelephone("15985268904"));
//		System.out.println(new LiCaiNongChangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.lcfarm.com/api/user/check.htm";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json;charset=UTF-8"),
							"{\"checkType\":\"PHONE\",\"bussinessType\":\"PBAND\",\"PHONE\":\""+account+"\"}");
			Request request = new Request.Builder().url(url)
					.addHeader("loginSource", "PC")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.lcfarm.com")
					.addHeader("Referer", "https://www.lcfarm.com/register.html?channel=pcgwzc2")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已注册")) {
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
