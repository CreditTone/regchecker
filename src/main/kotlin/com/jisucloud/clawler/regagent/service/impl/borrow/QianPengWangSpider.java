package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class QianPengWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "钱盆网(www.qianpen.com)是一家广西P2P平台,南宁市互联网金融行业协会副会长单位,为P2P出借用户提供网络出借、小额贷款、网络借贷信息中介等融资服务。";
	}

	@Override
	public String platform() {
		return "qianpen";
	}

	@Override
	public String home() {
		return "qianpen.com";
	}

	@Override
	public String platformName() {
		return "钱盆网";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new QianPengWangSpider().checkTelephone("15985268904"));
//		System.out.println(new QianPengWangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.qianpen.com/user-center/check-phone-zrr-lc?phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qianpen.com")
					.addHeader("Referer", "https://www.qianpen.com/user-center/register?u_atype=0&u_asec=099%23KAFEa7EjEmoEhGTLEEEEEpEQz0yFD6ghDu9MA6gFSuyYW60hZX%2FEV60wD8ITEEltl9llVF2TdzJLPMhEZoBWE7E5t3Tvlludt3iSlBbEW7aSdlxglaTZE7Exl9LHEcudt3JMOo8jvtpmuKhYfYFEF%2FnQw%2FdM%2F3iGvcvK40lxN9%2BjUhITEEltl3llW9kTdzJLPMhEZoB4E7EFEI1CbNQTEE7EERpCsEFEpE%2FSuSbx7GCqt37MlXZdtOJStTLtsyaGC3iSh3nP%2F3oYt37MlXZdtCwrE7EhtloF16%2BpBEFE13iSEp8ZnqNKAYFEmY15b%2FCdCwUQrjDt9235rAXQPEz6zqFnHshnwBZgSOIVNOFRi2wHc0oZPQbqrPIWcLYf9BZLDRbBrR7pi2iT6AXAPGDtUfpCc02A1SZYAi8BPsO3l2w3WEFE5YHH6YThbJmSoZdIkmwYBwD4k4A6m%2FP%2FMhA41VGZQioV6xCfZZpAIwEsMNLoy%2F5Ccv64JmDVrCT4kLH6ma2GkLFDBqeIo%2FoboaSCiFQsuBfr2hmhE7Eht3alluZdlYFETKxqAjJ5E7EFt37EFJMTEEvSdXylEyaStQAXE7EFEE1CbY%3D%3D")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("false");
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
