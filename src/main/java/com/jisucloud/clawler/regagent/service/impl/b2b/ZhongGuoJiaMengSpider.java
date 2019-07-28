package com.jisucloud.clawler.regagent.service.impl.b2b;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZhongGuoJiaMengSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "中国加盟网致力于在中国建立一个架构完整、内容丰富、分析客观、一个能让中小创业者与品牌商沟通最大的网络平台。在这样的志向和目标的推动下，网站名确定为“中国加盟网”。";
	}

	@Override
	public String platform() {
		return "jmw";
	}

	@Override
	public String home() {
		return "jmw.com";
	}

	@Override
	public String platformName() {
		return "中国加盟网";
	}

	@Override
	public String[] tags() {
		return new String[] {"招商加盟" ,"生意"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "13953670000");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://person.jmw.com.cn/check_infos.php";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("person_number", account)
	                .add("type", "phone")
	                .add("telephone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "person.jmw.com.cn")
					.addHeader("Referer", "http://person.jmw.com.cn/registered.php")
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
