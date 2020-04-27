package com.jisucloud.clawler.regagent.service.impl.shop;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "suning.com", 
		message = "苏宁易购，是苏宁易购集团股份有限公司旗下新一代B2C网上购物平台，现已覆盖传统家电、3C电器、日用百货等品类。2011年，苏宁易购强化虚拟网络与实体店面的同步发展，不断提升网络市场份额。", 
		platform = "suning", 
		platformName = "苏宁易购", 
		tags = { "购物" , "电器" }, 
		testTelephones = { "18210530000", "18212345678" })
public class SuNingSpider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://reg.suning.com/person.do?myTargetUrl=https%3A%2F%2Fwww.suning.com%2F");smartSleep(2000);
			chromeDriver.findElementByLinkText("同意并继续").click();
			smartSleep(1000);
			chromeDriver.findElementById("mobileAlias").sendKeys(account);
			chromeDriver.findElementById("sendSmsCode").click();
			smartSleep(2000);
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
		return HookTracker.builder().addUrl("ajaxCheckAliasEPP.do").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (messageInfo.getOriginalUrl().contains("sms.do")) {
			return DEFAULT_HTTPRESPONSE;
		}
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("线上已存在");
	}

}
