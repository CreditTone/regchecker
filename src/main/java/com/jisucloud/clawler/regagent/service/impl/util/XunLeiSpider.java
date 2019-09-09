package com.jisucloud.clawler.regagent.service.impl.util;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "xunlei.com", 
		message = "迅雷是全球领先的共享计算与区块链技术创新企业,公司成立于2003年,基于深耕十几年、获得国际专利的P2SP下载加速技术优势,面向个人用户和企业用户打造了丰富的下载加速器。", 
		platform = "xunlei", 
		platformName = "迅雷", 
		userActiveness = 0.7f,
		tags = { "系统工具", "影音" }, 
		testTelephones = { "18611216720", "18210538513" })
public class XunLeiSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://zhuce.kankan.com/regapi?callback=jsonp"+System.currentTimeMillis()+"&op=checkBind&account="+account+"&type=1&response=jsonp";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "zhuce.kankan.com")
					.addHeader("Referer", "http://u.kankan.com/register.html?regfrom=KK_001")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("binded\":1")) {
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
