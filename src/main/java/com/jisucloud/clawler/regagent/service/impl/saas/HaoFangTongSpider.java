package com.jisucloud.clawler.regagent.service.impl.saas;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "hftsoft.com", 
		message = "好房通｜房产中介系统行业标准引领者。好房通ERP——是国内先进的房产中介管理系统，集销售管理与OA办公于一体,是房产中介管理与营销不可或缺的办公系统房管软件，好房通房产中介管理软件让中介.", 
		platform = "hftsoft", 
		platformName = "好房通", 
		tags = { "OA系统", "办公软件" }, 
		testTelephones = { "13771025665", "18209649992" })
public class HaoFangTongSpider extends PapaSpider {

	
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://www.hftsoft.com/Home/Register/checkPhone?action=checkPhone&mobile="+account+"&tmp=0.39787207731392094";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.hftsoft.com/user/register.shtml")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().contains("exist");
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
