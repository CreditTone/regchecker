package com.jisucloud.clawler.regagent.service.impl.util;

import com.deep007.goniub.okhttp.OKHttpUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "eqxiu.com", 
		message = "易企秀h5页面制作工具,微信营销推广专业场景制作平台,企业宣传、音乐相册、电子贺卡、邀请函、微杂志等一键生成H5酷炫页面,易企秀为您助力移动自主营销!", 
		platform = "eqxiu", 
		platformName = "易企秀", 
		tags = { "工具" , "企业宣传" }, 
		testTelephones = { "15901537458", "18212345678" })
public class YiQiXiuSpider extends PapaSpider {

	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClientWithRandomProxy();


	public boolean checkTelephone(String account) {
		try {
			String url = "http://passport.eqxiu.com/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("password", "xas123adsdaa")
	                .add("rememberMe", "true")
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.eqxiu.com/auth/login")
					.addHeader("Origin", "http://www.eqxiu.com")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("密码错误")) {
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
