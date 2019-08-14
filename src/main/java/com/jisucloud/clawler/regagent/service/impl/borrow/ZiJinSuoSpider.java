package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "zijinsuo.com", 
		message = "紫金所是手机理财应用软件，把守护资金出借人和小微企业的资金和数据安全作为企业发展的宗旨 紫金所存管模式采取了最被监管部门认可的银行直接存管模式，将为出借人和借款人分别开设独立的个人银行存管账户该平台。", 
		platform = "zijinsuo", 
		platformName = "紫金所", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class ZiJinSuoSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.zijinsuo.com/register.do?action=restMobile&mobile=" + account + "&_=" +System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.zijinsuo.com")
					.addHeader("Referer", "https://www.zijinsuo.com")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string().contains("1");
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
