package com.jisucloud.clawler.regagent.service.impl.money;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "cnfol.com", 
		message = "中金在线-中国人的金融门户网站,覆盖财经、股票、 证券、金融、港股、行情、基金、债券、期货、外汇、保险、银行、博客、股票分析软件等多种面向个人和企业的服务。", 
		platform = "cnfol", 
		platformName = "中金在线", 
		tags = { "金融资讯", "期货" , "贵金属" , "股票" }, 
		testTelephones = { "15985260000", "18212345678" })
public class ZhongJinZaiXianSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.cnfol.com/userregister/ajaxcheckmobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("type", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.cnfol.com")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("Referer", "https://passport.cnfol.com/userregister?rt=aHR0cHM6Ly9wYXNzcG9ydC5jbmZvbC5jb20v")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已被注册")) {
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
