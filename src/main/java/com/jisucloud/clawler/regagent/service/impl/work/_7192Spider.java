package com.jisucloud.clawler.regagent.service.impl.work;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class _7192Spider extends PapaSpider {

	@Override
	public String message() {
		return "全影网是全国影楼行业领先门户网站,汇聚顶尖影楼人才。涵盖摄影、化妆、婚纱摄影、影楼资讯、影楼摄影作品、摄影教程、摄影器材、化妆教程、影楼化妆造型、影楼管理。";
	}

	@Override
	public String platform() {
		return "7192";
	}

	@Override
	public String home() {
		return "7192.com";
	}

	@Override
	public String platformName() {
		return "全影网";
	}

	@Override
	public String[] tags() {
		return new String[] {"摄影", "化妆", "造型"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18720982607", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://m.7192.com/ajax/cm";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://m.7192.com/passport/register")
					.post(createUrlEncodedForm("e="+account))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("0")) {
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
