package com.jisucloud.clawler.regagent.service.impl.social;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import me.kagura.JJsoup;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.jsoup.Connection;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@UsePapaSpider
public class MainMainSpider implements PapaSpider, AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
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
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://acc.maimai.cn/login");
			chromeDriver.addAjaxHook(this);
			Thread.sleep(1000);
			chromeDriver.findElementByCssSelector(".switch_mode_button").click();
			Thread.sleep(1000);
			chromeDriver.findElementByClassName("loginPhoneInput").sendKeys(account);
			chromeDriver.findElementByCssSelector(".getcode").click();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
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
		System.out.println(contents.getTextContents());
		try {
			JSONObject result = JSON.parseObject(contents.getTextContents());
			if (result.getString("result").equalsIgnoreCase("ok")
				&& result.size() == 1) {
				checkTel = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
