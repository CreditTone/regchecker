package com.jisucloud.clawler.regagent.service.impl.shop;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


import org.openqa.selenium.WebElement;

@Slf4j
@PapaSpiderConfig(
		home = "juanpi.com", 
		message = "卷皮网，服务消费者日常生活所需的平价生活电子商务平台，专注为消费者提供平价商品和更好购物体验，是一家专注高性价比商品的移动电商。2012年9月正式推出，同时运营有网站和移动App。2014年7月卷皮曾获得由汉理、纽信共同投入的5000万人民。", 
		platform = "juanpi", 
		platformName = "卷皮网", 
		tags = { "9.9包邮" , "购物" }, 
		testTelephones = { "18210008513", "18210538513" })
public class JuanPiSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	boolean vcodeSuc = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://user.juanpi.com/login?returnurl=https://user.juanpi.com/setting/regsuccess/rf/");smartSleep(2000);
			chromeDriver.findElementById("account").sendKeys(account);
			chromeDriver.findElementById("inter_code").sendKeys("asd90123das");
			for (int i = 0; i < 5; i++) {
				if (chromeDriver.checkElement("input[class='normal-input code-input']")) {
					String vcode = "abcd";
					WebElement code = chromeDriver.findElementByCssSelector("input[class='normal-input code-input']");
					code.clear();
					code.sendKeys(vcode);
				}
				chromeDriver.findElementByCssSelector("input[type=submit]").click();smartSleep(3000);
				if (vcodeSuc) {
					break;
				}
			}
			
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
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("login/checkLogin").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	boolean checkTel = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		vcodeSuc = true;
		checkTel = contents.getTextContents().contains("password");
	}

}
