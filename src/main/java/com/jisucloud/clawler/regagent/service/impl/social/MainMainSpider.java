package com.jisucloud.clawler.regagent.service.impl.social;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@UsePapaSpider
public class MainMainSpider extends PapaSpider implements AjaxHook {
	
	
	
	@Override
	public String message() {
		return "脉脉(maimai.cn),中国领先的职场实名社交平台,利用科学算法为职场人拓展人脉,降低商务社交门槛,实现各行各业交流合作。";
	}

	@Override
	public String platform() {
		return "mainmain";
	}

	@Override
	public String home() {
		return "taou.com";
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210530000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String postData = "info_type=2&account=%2B86-"+account+"&new_fr=1&password=213e321deqdasdasdasd&dev_type=3&imei=352284040670808";
			String url = "https://open.taou.com/maimai/user/v3/login?u=&access_token=&version=4.5.8&ver_code=android_1135&channel=wdj&vc=19&udcode=572719103&push_permit=1&net=wifi";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.2; 8692-A00 Build/KOT49H)/{QiKU 8692-A00}  [Android 4.4.2/19]/MaiMai 4.5.8(1135)")
					.addHeader("Host", "open.taou.com")
					.post(FormBody.create(MediaType.get("application/x-www-form-urlencoded"), postData))
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = response.body().string();
			if (res.contains("User/Password") || res.contains("21009")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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

	@Override
	public String platformName() {
		return "脉脉";
	}

	@Override
	public String[] tags() {
		return new String[] { "社交", "找合作", "求职", "招聘", "工具" };
	}

	@Override
	public HookTracker getHookTracker() {
		return HookTracker.builder()
				.addUrl("/login_code?mobile")
				.isGet()
				.build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		try {
			JSONObject result = JSON.parseObject(contents.getTextContents());
			if (result.getString("result").equalsIgnoreCase("ok")
				&& result.size() == 1) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
