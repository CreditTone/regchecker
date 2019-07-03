package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.http.OKHttpUtil;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
//@UsePapaSpider  行为分析反扒
public class DouYuSpider implements PapaSpider {

	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClientWithRandomProxy();

	@Override
	public String message() {
		return "斗鱼- 每个人的直播平台提供高清、快捷、流畅的视频直播和游戏赛事直播服务,包含英雄联盟lol直播、穿越火线cf直播、dota2直播、美女直播等各类热门游戏赛事直播。";
	}

	@Override
	public String platform() {
		return "douyu";
	}

	@Override
	public String home() {
		return "douyu.com";
	}

	@Override
	public String platformName() {
		return "斗鱼直播";
	}

	@Override
	public String[] tags() {
		return new String[] {"社区", "游戏" , "交友"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13925306966", "18210538513");
	}
	
	

	@Override
	public boolean checkTelephone(String account) {
		try {
			
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

}
