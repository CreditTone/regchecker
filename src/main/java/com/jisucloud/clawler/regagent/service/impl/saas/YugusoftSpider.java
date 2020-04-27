package com.jisucloud.clawler.regagent.service.impl.saas;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "yugusoft.com", 
		message = "鱼骨让项目管理更轻松,以任务为基础的目标管理平台,帮助您更好管理企业的人、事和目标,Windows、Mac、Android、iPhone、微信等多终端提升企业执行力。", 
		platform = "yugusoft", 
		platformName = "鱼骨办公", 
		tags = { "办公软件" , "沟通平台" }, 
		testTelephones = { "18212345678","18515290000" })
public class YugusoftSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://app01.yugusoft.com/ftask/proxy/v2/user/login.json?plat=web&build=999999";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("identify", account)
	                .add("password", "na13210czme")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "app01.yugusoft.com")
					.addHeader("Referer", "https://app01.yugusoft.com/ftask/web/index.html#/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			return res.contains("密码错误") || res.contains("验证码有误");
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
