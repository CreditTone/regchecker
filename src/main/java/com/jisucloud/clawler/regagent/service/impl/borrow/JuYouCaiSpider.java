package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;

import java.util.UUID;


@Slf4j
@PapaSpiderConfig(
		home = "jyc99.com", 
		message = "聚优财是浙江聚有财金融服务外包有限公司旗下运营的理财APP，原为“聚有财”升级；旨在宣传优生活，优选择；公司致力于成为一个针对中国中产阶级的专业互联网资产管理平台.关注互联网金融，投资理财，资产配置方面，坚持设计清晰、透明的理财产品；关注用户个性化、定制化需求。", 
		platform = "jyc99", 
		platformName = "聚优财", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "13910002045", "18210538513" })
public class JuYouCaiSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.jyc99.com/api/user/loginreg/dologin";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userPassword", "casda0sdjj1231")
	                .add("userMobile", account)
	                .add("type", "1")
	                .add("terminalType", "1")
	                .add("macAddress", UUID.randomUUID().toString())
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.jyc99.com")
					.addHeader("contentType", "application/json; charset=utf-8")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("Referer", "https://www.jyc99.com/userreg/login?returnUrl=/product")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("帐号或密码") || res.contains("帐号已锁定")) {
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
