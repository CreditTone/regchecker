package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@PapaSpiderConfig(
		home = "edai.com", 
		message = "摩尔龙致力于为社会普通大众群体提供更加简单、透明及全方位的金融服务。公司业务覆盖全国，每年为大量客户提供包括：信用贷款、抵押贷款、装修贷款、旅游贷款、经营贷款、企业贷款等服务，满足您的全部资金需求。一个电话马上贷款咨询！", 
		platform = "edai", 
		platformName = "摩尔龙金融", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "15985268004", "18210538513" })
@Slf4j
public class MoerlongSpider extends PapaSpider {
	
	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "www.edai.com");
		headers.put("Referer", "http://www.edai.com/user/lostPassword/");
		return Headers.of(headers);
	}
	
	private boolean checkImgCode() {
		String img = "http://www.edai.com/authImg/show/3" + System.currentTimeMillis();
		Response response;
		for (int i = 0; i < 5; i++) {
			try {
				response = get(img);
				if (response != null) {
					byte[] body = response.body().bytes();
					String imageCode = OCRDecode.decodeImageCode(body);
					String checkUrl = "http://www.edai.com/authimg/verify/?r=0.6649229691142"+new Random().nextInt(1000)+"&authNum="+imageCode;
					Response checkresponse = get(checkUrl);
					if (checkresponse.body().string().contains("1")) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.edai.com/user/exist/?phone=" + account;
			if (checkImgCode()) {
				FormBody formBody = new FormBody
		                .Builder()
		                .build();
				Request request = new Request.Builder().url(url)
						.headers(getHeader())
						.post(formBody)
						.build();
				Response response = okHttpClient.newCall(request).execute();
				if (response != null) {
					return response.body().string().contains("1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return checkTelephone(account);
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
