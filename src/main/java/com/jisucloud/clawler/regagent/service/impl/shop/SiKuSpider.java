package com.jisucloud.clawler.regagent.service.impl.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "secoo.com", 
		message = "寺库奢侈品网站(secoo.com)作为全球最大的奢侈品购物服务平台,涉及了奢侈品网上销售、奢侈品实体休闲会所、奢侈品鉴定与养护服务等主营业务,100%正品保证,全球奢品。", 
		platform = "secoo", 
		platformName = "寺库奢侈品网", 
		tags = { "购物" , "首饰" , "奢侈品" }, 
		testTelephones = { "18515290710", "18212345678" })
public class SiKuSpider extends PapaSpider {

	String cck = "";
	
	private Request builderRequest(String url,FormBody formBody) {
		return new Request.Builder().url(url)
				.addHeader("User-Agent", "android")
				.addHeader("Host", "user-center.secoo.com")
				.addHeader("X-Tingyun-Id", "IYSvMxBahhI;c=2;r=1692348213;")
				.addHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIiLCJuYmYiOjE1NjA4MjYxMjksInVzZXJJZCI6MzYyNjU0NTU5ODM2LCJkZXZpY2VJZCI6IjM1MjI4NDA0MDY3MDgwOF8wODo2RDo0MTpENTo3QTo2QSIsInZlcnNpb24iOiIxIn0.8e4PLzaIQDeMdNWjILYnAaE7k76kSgNI8GsdmYp7m5M")
				.addHeader("screen-width", "480")
				.addHeader("mac", "08:6D:41:D5:7A:6A")
				.addHeader("uuid", "352284040670808_08:6D:41:D5:7A:6A")
				.addHeader("app-id", "644873678")
				.addHeader("platform", "8692-A00")
				.addHeader("device-id", "352284040670808_08:6D:41:D5:7A:6A")
				.addHeader("screen-height", "800")
				.addHeader("imei", "352284040670808")
				.addHeader("channel", "baiduzhushou")
				.addHeader("sysver", " 4.4.2")
				.addHeader("product", "1")
				.addHeader("app-ver", "7.5.2")
				.addHeader("sysverlevel", "19")
				.addHeader("platform-ver", "4.4.2")
				.addHeader("platform-type", "1")
				.post(formBody)
				.build();
	}

	
	String getImageCode() {
		try {
			String url = "https://user-center.secoo.com/service/appapi/captcha/v1?bizType=findpwd";
			FormBody formBody = new FormBody
	                .Builder()
	                .build();
			Request request = builderRequest(url, formBody);
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string()).getJSONObject("object");
			cck = result.getString("cck");
			byte[] body = java.util.Base64.getDecoder().decode(result.getString("base64Img"));
			return OCRDecode.decodeImageCode(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "123x";
	}
	


	public boolean checkTelephone(String account) {
		for (int i = 0; i < 5; i++) {
			try {
				String url = "https://user-center.secoo.com/service/appapi/user/findpwd/verify";
				String verifyCode = getImageCode();
				FormBody formBody = new FormBody
		                .Builder()
		                .add("mobile", account)
		                .add("cck", cck)
		                .add("verifyCode", verifyCode)
		                .build();
				Request request = builderRequest(url, formBody);
				Response response = okHttpClient.newCall(request).execute();
				String res = StringUtil.unicodeToString(response.body().string());
				if (res.contains("验证码错误")) {
					continue;
				}
				if (res.contains("用户不存在")) {
					return false;
				}
				if (res.contains("成功")) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
