package com.jisucloud.clawler.regagent.service.impl.work;

import com.jisucloud.clawler.regagent.http.OKHttpUtil;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

@Slf4j
@UsePapaSpider
public class ZhongGuoRenCaiWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();

	@Override
	public String message() {
		return "中国人才网,找工作、找人才首选!▼免费求职网-中国人才招聘网:招聘信息超多,有效职位极广,个人简历齐全;企业招聘人才、个人求职找工作,就上中国人才网。";
	}

	@Override
	public String platform() {
		return "cnjob";
	}

	@Override
	public String home() {
		return "cnjob.com";
	}

	@Override
	public String platformName() {
		return "中国人才网";
	}

	@Override
	public String[] tags() {
		return new String[] {"求职" , "招聘"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhongGuoRenCaiWangSpider().checkTelephone("18230012895"));
//		System.out.println(new ZhongGuoRenCaiWangSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.cnjob.com/default/reg/checkUser";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userName", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "www.cnjob.com")
					.addHeader("Referer", "http://www.cnjob.com/default/findpsw")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("用户名已存在")) {
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
