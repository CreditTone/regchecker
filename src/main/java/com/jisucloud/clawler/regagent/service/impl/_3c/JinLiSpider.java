package com.jisucloud.clawler.regagent.service.impl._3c;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class JinLiSpider extends PapaSpider {

	@Override
	public String message() {
		return "Amigo（朋友）是金立旗下，基于Android进行深度优化、定制、开发的第三方Android系统ROM，专为中国人习惯设计，全面改进原生体验。能够带给国内用户更为贴心的Android智能手机体验。 ";
	}

	@Override
	public String platform() {
		return "amigo";
	}

	@Override
	public String home() {
		return "amigo.com";
	}

	@Override
	public String platformName() {
		return "金立Amigo";
	}

	@Override
	public String[] tags() {
		return new String[] {"3c", "科技" ,"智能手机"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18767922855", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://id.amigo.cn/members/loginauthorize?client_id=AC9ABF21CD604450B27DF05D921643D2&redirect_uri=https:%2F%2Fwww.amigo.cn%2F&response_type=code&state=&title=";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://id.amigo.cn/members/start")
					.post(createJsonForm("{\"tn\":\""+account+"\",\"vid\":\"\",\"vtx\":\"\",\"p\":\"dasa1dsaesa1213\",\"vty\":\"vtext\"}"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("1011")) {
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
