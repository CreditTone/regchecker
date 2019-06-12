package com.jisucloud.clawler.regagent.service.impl.work;

import com.jisucloud.clawler.regagent.http.OKHttpUtil;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DuoShangWangSpider implements PapaSpider {

	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();

	@Override
	public String message() {
		return "多商网是珠三角云供应链平台,为淘宝天猫网店/线下实体店提供男女装,美妆,鞋包,家电母婴等工厂货源批发代销、一件代发、代理加盟。";
	}

	@Override
	public String platform() {
		return "yifatong";
	}

	@Override
	public String home() {
		return "yifatong.com";
	}

	@Override
	public String platformName() {
		return "多商网";
	}

	@Override
	public String[] tags() {
		return new String[] {"B2B" , "电商"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new DuoShangWangSpider().checkTelephone("18210538513"));
//		System.out.println(new DuoShangWangSpider().checkTelephone("18210538511"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.ecduo.cn/new_index.php?app=member&act=ajax_check";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("param", account)
	                .add("name", "name")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.ecduo.cn")
					.addHeader("Referer", "https://www.ecduo.cn/register/")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("已注册")) {
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
