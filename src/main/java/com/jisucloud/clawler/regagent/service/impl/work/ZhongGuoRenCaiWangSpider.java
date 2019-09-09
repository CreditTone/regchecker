package com.jisucloud.clawler.regagent.service.impl.work;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "cnjob.com", 
		message = "中国人才网,找工作、找人才首选!▼免费求职网-中国人才招聘网:招聘信息超多,有效职位极广,个人简历齐全;企业招聘人才、个人求职找工作,就上中国人才网。", 
		platform = "cnjob", 
		platformName = "中国人才网", 
		tags = { "求职" , "招聘" }, 
		testTelephones = { "18210538513", "18230012895" })
public class ZhongGuoRenCaiWangSpider extends PapaSpider {

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
