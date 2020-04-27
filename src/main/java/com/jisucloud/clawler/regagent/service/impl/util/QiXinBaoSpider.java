package com.jisucloud.clawler.regagent.service.impl.util;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


@PapaSpiderConfig(
		home = "qixin.com", 
		message = "启信宝为您提供全国企业信用信息查询服务,包括企业注册信息查询、企业工商信息查询、企业信用查询、企业信息查询等相关信息查询!还可以深入了解企业相关的法人股东,企业。", 
		platform = "qixin", 
		platformName = "启信宝", 
		tags = { "工具" }, 
		testTelephones = { "18212345678", "18210538000" })
public class QiXinBaoSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			//chromeDriver.get("https://www.qixin.com/");
			chromeDriver.get("https://www.qixin.com/auth/login?return_url=%2F");
			chromeDriver.findElementByCssSelector("input[placeholder=请输入手机号码]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[placeholder=请输入密码]").sendKeys("xkaocgwicb123");
			chromeDriver.findElementByLinkText("登录").click();
			smartSleep(3000);
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
	public HookTracker getHookTracker() {
		return HookTracker.builder().addUrl("api/user/login").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (contents.getTextContents().contains("密码错误")) {
			checkTel = true;
		}
	}

}
