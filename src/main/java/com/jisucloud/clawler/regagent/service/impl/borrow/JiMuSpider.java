package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.openqa.selenium.WebElement;

import java.util.*;

@PapaSpiderConfig(
		home = "jimu.com", 
		message = "积木盒子是国内领先的网络借贷信息中介平台，专注于运用互联网和技术手段打通金融服务中存在的痛点，为个人、微型企业提供稳健、高效、轻松的借贷撮合服务。", 
		platform = "jimu", 
		platformName = "积木盒子", 
		tags = {"理财", "P2P", "借贷" }, 
		testTelephones = {"13261165342", "18212345678"})
public class JiMuSpider extends PapaSpider implements AjaxHook{
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确


	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#CaptchaImage");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	String code;

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.get("https://www.jimu.com/User/Register");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("UserMobile").sendKeys(account);
			chromeDriver.findElementById("LoginPass").sendKeys("sadask12m1ds");
			for (int i = 0; i < 5; i++) {
				code = getImgCode();
				WebElement validate = chromeDriver.findElementById("Captcha");
				validate.clear();
				validate.sendKeys(code);
				chromeDriver.mouseClick(chromeDriver.findElementById("goRegister"));smartSleep(3000);
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
	public HookTracker getHookTracker() {
		return HookTracker.builder()
				.addUrl("User/Register/Validate")
				.build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (!contents.getTextContents().contains("验证码")) {
			vcodeSuc = true;
			checkTel = contents.getTextContents().contains("已经存在");
		}
		
	}

}
