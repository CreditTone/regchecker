package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.http.OKHttpUtil;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class GuoShuCaiFuSpider implements PapaSpider {

	private boolean checkTel = false;
	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();
	
	@Override
	public String message() {
		return "果树财富P2P平台专注于互联网金融p2p车贷,p2p贷款,p2p网贷,提供创新型金融产品,传递现代金融服务理念。";
	}

	@Override
	public String platform() {
		return "goodsure";
	}

	@Override
	public String home() {
		return "goodsure.com";
	}

	@Override
	public String platformName() {
		return "果树财富";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			okHttpClient.newCall(new Request.Builder().url("https://www.baidu.com/link?url=xGSj_vb0FEkwX3Mjy3J-bIsdUOaKYHQkShsBIYyBVffaIwS_-qLb5n01mBuzLXP2&wd=&eqid=b6b51d51001a87e4000000025cfe1047").build()).execute();
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
			System.out.println(res);
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
