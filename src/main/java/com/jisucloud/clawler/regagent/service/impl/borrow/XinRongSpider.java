package com.jisucloud.clawler.regagent.service.impl.borrow;

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

import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "xinrong.com", 
		message = "信融财富是一家专业提供网络借贷信息服务中介平台,平台运营时间超过六年,获得上市系融资。平台致力于为融资方高效解决资金需求,为出借方提供更便捷的投资服务。", 
		platform = "xinrong", 
		platformName = "信融财富", 
		tags = { "p2p" , "借贷" }, 
		testTelephones = { "13910250000", "18212345678" })
public class XinRongSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean success = false;
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector(".valiPicCon #img-captcha");
				chromeDriver.mouseClick(img);smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	String code;


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://www.xinrong.com/2.0/login2.0.html");smartSleep(3000);
			chromeDriver.findElementById("rapid-userName").sendKeys(account);
			chromeDriver.findElementById("rapid-userPw").sendKeys("rapi1serPw");
			for (int i = 0; i < 5; i++) {
				if (chromeDriver.checkElement(".valiPicCon #img-captcha")) {
					String vcode = getImgCode();
					WebElement rapid = chromeDriver.findElementByCssSelector(".valiPicCon #rapid-captcha");
					rapid.clear();
					rapid.sendKeys(vcode);
				}
				chromeDriver.findElementByCssSelector("input[class='submit ui-button']").click();smartSleep(2000);
				if (success) {
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
		return HookTracker.builder()
				.addUrl("/login/login.jso")
				.isPost()
				.build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (contents.isText() && contents.getTextContents().contains("验证码")) {
			return;
		}
		success = true;
		if (contents.isText() && contents.getTextContents().contains("9999")) {
			checkTel = true;
		}
	}

}
