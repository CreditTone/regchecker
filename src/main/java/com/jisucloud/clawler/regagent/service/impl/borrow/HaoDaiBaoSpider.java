package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import com.deep007.spiderbase.okhttp.OKHttpUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "haodaibao.com", 
		message = "好贷宝，由中融金（北京）科技有限公司（以下简称\"中融金\"）自主研发并自主运营的网络借贷信息中介服务平台。致力于搭建场景大数据平台和金融信息服务科技生态体系，为个人用户和企业用户提供专业的网络借贷信息中介服务。 ", 
		platform = "haodaibao", 
		platformName = "好贷宝", 
		tags = { "贷超", "贷超" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class HaoDaiBaoSpider extends PapaSpider {


	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.haodaibao.com/f/register/queryPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobilePhone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.haodaibao.com")
					.addHeader("Referer", "https://www.haodaibao.com/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("false")) {
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
