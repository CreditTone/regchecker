package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RongYiJieSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "容易借app，急用钱的时候，容易借贷款来帮你，多种贷款，按需求匹配。利息低，放款快！专注于小额借款、专注无押贷款、借钱于一体的贴身贷款顾问。纯线上贷款，快至3分钟审核，30秒放款。贷款app包含所有的小额极速贷、小额分期贷、信用卡代还、大额借贷等借钱方式，一次性帮您网罗全。在您缺钱的时候帮您解决燃眉之急。";
	}

	@Override
	public String platform() {
		return "casheasy";
	}

	@Override
	public String home() {
		return "casheasy.com";
	}

	@Override
	public String platformName() {
		return "容易借款";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("媒体", new String[] { });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new RongYiJieSpider().checkTelephone("18210538000"));
//		System.out.println(new RongYiJieSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.casheasy.cn:8082/login?username="+account+"&password=gv%2BnF3Vv7KF07Jd%2FVOyYzw%3D%3D";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.2; 8692-A00 Build/KOT49H)")
					.addHeader("Host", "www.casheasy.cn:8082")
					.post(FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), ""))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("ueAXLrCtBN0oGxsOn1XukUrlnD")) {
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
