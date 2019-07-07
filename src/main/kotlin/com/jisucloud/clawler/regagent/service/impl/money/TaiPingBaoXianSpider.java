package com.jisucloud.clawler.regagent.service.impl.money;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

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
public class TaiPingBaoXianSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	
	@Override
	public String message() {
		return "中国太平保险集团有限责任公司，是我国第一家跨国金融保险集团公司，其品牌历史源远流长，可以追溯至1929年创立的太平水火保险公司、1931年创立的中国保险股份有限公司和1949年成立的香港民安保险有限公司。";
	}

	@Override
	public String platform() {
		return "cntaiping";
	}

	@Override
	public String home() {
		return "cntaiping.com";
	}

	@Override
	public String platformName() {
		return "太平保险";
	}

	@Override
	public String[] tags() {
		return new String[] {"理财", "保险"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15901537458", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://sso.cntaiping.com/sso/register/registerCheck";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "sso.cntaiping.com")
					.addHeader("Referer", "https://sso.cntaiping.com/sso/register?service=http://www.cntaiping.com/loginAct/loginSuccess.jspx?gotoUrl=http://www.cntaiping.com/&_source_target=gwPC")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已注册")) {
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
