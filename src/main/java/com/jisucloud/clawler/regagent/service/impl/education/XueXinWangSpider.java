package com.jisucloud.clawler.regagent.service.impl.education;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "chsi.com", 
		message = "中国高等教育学生信息网(简称“学信网”)由全国高等学校学生信息咨询与就业指导中心(以下简称“中心”)于2002年5月注册并开通，由中心控股的学信咨询服务有限公司负责运营", 
		platform = "chsi", 
		platformName = "学信网", 
		tags = { "学历" }, 
		testTelephones = { "15210000000", "18210538513" })
public class XueXinWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://account.chsi.com.cn/account/checkmobilephoneother.action";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mphone", account)
	                .add("dataInfo", account)
	                .add("optType", "REGISTER")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "account.chsi.com.cn")
					.addHeader("Referer", "https://account.chsi.com.cn/account/preregister.action?from=chsi-home")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("false")) {
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
