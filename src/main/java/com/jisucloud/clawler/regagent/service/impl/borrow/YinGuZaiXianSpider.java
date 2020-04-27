package com.jisucloud.clawler.regagent.service.impl.borrow;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "yingujr.com", 
		message = "银谷在线(App同名,网址为:www.yingujr.com)是东方银谷(北京)投资管理有限公司(简称东方银谷)旗下大型网络借贷信息撮合平台。于2016年7月15日上线,目前已实现资金盈利。", 
		platform = "yingujr", 
		platformName = "银谷在线", 
		tags = {"P2P" , "借贷" }, 
		testTelephones = {"13910252000", "18212345678" },
		exclude = true
		)
public class YinGuZaiXianSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, false);
			String url = "https://www.yingujr.com/login";
			chromeDriver.get(url);smartSleep(6000);
			chromeDriver.findElementByCssSelector("input.account").sendKeys(account);
			chromeDriver.findElementByCssSelector("input.password").sendKeys(account);
			chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("div.buttonBox"));smartSleep(3000);
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
		return HookTracker.builder().addUrl("auth/checkLogin").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("用户密码不正确") || contents.getTextContents().contains("被锁定");
	}

}
