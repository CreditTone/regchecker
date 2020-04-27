package com.jisucloud.clawler.regagent.service.impl.health;

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
		home = "pharmacyonline.com", 
		message = "澳洲PO药房是澳洲本土大型综合类药房,也是首家开通直邮中国服务的澳洲本土药房,提供数万母婴用品、保健品、护肤化妆品、洗护用品等各类产品选购,全中文购物界面。", 
		platform = "pharmacyonline", 
		platformName = "澳洲PO药房", 
		tags = { "购药", "用药" }, 
		testTelephones = { "15008276300", "18212345678" })
public class PharmacySpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	
	private boolean checkTelephone = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://cn.pharmacyonline.com.au/security/passwordforgotten?step=account");
			chromeDriver.addAjaxHook(this);
			smartSleep(1000);
			chromeDriver.findElementByCssSelector("input[name='username']").sendKeys(account);
			chromeDriver.findElementByClassName("forgot-step-input").click();
			smartSleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTelephone;
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
		return HookTracker.builder().addUrl("https://cn.pharmacyonline.com.au/api/member/check").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTelephone = contents.getTextContents().contains("cellphone");
	}

}
