package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.deep007.spiderbase.okhttp.OKHttpUtil;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "goodsure.com", 
		message = "果树财富P2P平台专注于互联网金融p2p车贷,p2p贷款,p2p网贷,提供创新型金融产品,传递现代金融服务理念。", 
		platform = "goodsure", 
		platformName = "果树财富", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13910252045", "18210538513" },
		ignoreTestResult = true)
public class GuoShuCaiFuSpider extends PapaSpider {

	private boolean checkTel = false;
	
	
	public boolean checkTelephone(String account) {
		try {
			okHttpClient.newCall(new Request.Builder().url("https://www.baidu.com/link?url=xGSj_vb0FEkwX3Mjy3J-bIsdUOaKYHQkShsBIYyBVffaIwS_-qLb5n01mBuzLXP2&wd=&eqid=b6b51d51001a87e4000000025cfe1047").build()).execute().body().close();
			String url = "https://www.goodsure.cn/home/login";
			Response response = okHttpClient.newCall(new Request.Builder().url(url).build()).execute();
			Document doc = Jsoup.parse(response.body().string());
			String csrf = doc.select("#csrf").attr("csrf");
			FormBody formBody = new FormBody
	                .Builder()
	                .add("cookietime", "0")
	                .add("account", account)
	                .add("password", "dasd139cnaq")
	                .add("+_csrf", csrf)
	                .build();
			Request request = new Request.Builder().url("https://api.goodsure.cn/account/login")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "api.goodsure.cn")
					.addHeader("Referer", "https://api.goodsure.cn/home/login")
					.post(formBody)
					.build();
			response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (!res.contains("账户不存在")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
		return checkTel;
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
