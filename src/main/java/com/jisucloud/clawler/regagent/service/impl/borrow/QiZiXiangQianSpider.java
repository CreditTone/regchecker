package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class QiZiXiangQianSpider extends PapaSpider {

	
	
	@Override
	public String message() {
		return "奇子向钱，是北京奇子投资管理有限公司旗下的互联网金融综合服务商,与奇子贷、资产卫士共同构成奇子金服的三大业务模块，专注于为投资者提供真实、透明、便捷的互联网理财及增值服务。";
	}

	@Override
	public String platform() {
		return "qzxq";
	}

	@Override
	public String home() {
		return "qzxq.com";
	}

	@Override
	public String platformName() {
		return "奇子向钱";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.qzxq.com/regCtrl/isLoginNameExist.do?0.8" + System.currentTimeMillis();
			FormBody formBody = new FormBody
	                .Builder()
	                .add("loginName", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.qzxq.com")
					.addHeader("Referer", "https://www.qzxq.com/regCtrl/register.do")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("true")) {
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
