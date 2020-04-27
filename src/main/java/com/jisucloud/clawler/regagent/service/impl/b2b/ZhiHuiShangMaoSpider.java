package com.jisucloud.clawler.regagent.service.impl.b2b;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.UUID;

@Slf4j
@PapaSpiderConfig(
		home = "zhsmjxc.com", 
		message = "智慧商贸 适用于中小企业,30秒注册,基础功能全匹配.支持离线操作,试用期14天智慧商贸 手机电脑都能用.24小时QQ服务,快速响应.为您解决一切软件使用问题。", 
		platform = "zhsmjxc", 
		platformName = "智慧商贸", 
		tags = { "saas" ,"财务软件" ,"生意" }, 
		testTelephones = { "18212345678", "13953679455" })
public class ZhiHuiShangMaoSpider extends PapaSpider {

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhiHuiShangMaoSpider().checkTelephone("18210538000"));
//		System.out.println(new ZhiHuiShangMaoSpider().checkTelephone("18212345678"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://web.zhsmjxc.com/UCenter-webapp//Register/IsEmailOrPhoneBeBound.json?transNo=" + UUID.randomUUID();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "web.zhsmjxc.com")
					.addHeader("Referer", "http://web.zhsmjxc.com/UCenter-webapp/Register/Init.htm?ProductType=0")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("手机号不可用")) {
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
