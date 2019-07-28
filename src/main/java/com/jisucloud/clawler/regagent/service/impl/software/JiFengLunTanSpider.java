package com.jisucloud.clawler.regagent.service.impl.software;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class JiFengLunTanSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "机锋论坛是全球最大、用户量最多、资源最全面的中文安卓论坛,拥有海量的ROM、游戏、软件资源,同时为玩家们提 供最新最热的安卓手机资源,各种刷机教程。搞机就上机锋网|GFAN.COM。";
	}

	@Override
	public String platform() {
		return "gfan";
	}

	@Override
	public String home() {
		return "gfan.com";
	}

	@Override
	public String platformName() {
		return "机锋论坛";
	}

	@Override
	public String[] tags() {
		return new String[] {"3c", "科技" ,"智能手机"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15510257873", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://my.gfan.com/isthisPhone";
			String postData = "cellphone="+account+"&editFlag=0&cellphoneFlag=1";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "http://my.gfan.com/login")
					.post(FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), postData))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("已被注册")) {
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
