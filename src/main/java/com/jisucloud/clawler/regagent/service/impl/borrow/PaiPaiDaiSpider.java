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

import java.util.Map;

import org.openqa.selenium.WebElement;

@Slf4j
@PapaSpiderConfig(
		home = "ppdai.com", 
		message = "拍拍贷（ppdai.com）美国纽交所上市企业，与招商银行达成存管的平台，专注信用借款和优质出借服务11年，已服务千万用户。轻松借款，额度高，资金链透明，注册资本10亿元。出借风险分散，期限灵活，借款额度高至20万。", 
		platform = "ppdai", 
		platformName = "拍拍贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13910250000", "18212345678" })
public class PaiPaiDaiSpider extends PapaSpider implements AjaxHook{
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean success = false;
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				chromeDriver.findElementByCssSelector(".changeCode").click();
				WebElement img = chromeDriver.findElementByCssSelector("#CodeImg");smartSleep(1000);
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
			chromeDriver.get("https://passport.ppdai.com/resetPassword.html");smartSleep(3000);
			chromeDriver.findElementById("inputName1").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				WebElement rapid = chromeDriver.findElementByCssSelector("#inputCode1");
				rapid.clear();
				String vcode = getImgCode();
				chromeDriver.jsInput(rapid, vcode);smartSleep(1000);
				chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("#validateBtn1"));smartSleep(2000);
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
				.addUrl("api/changemobile/password_step1")
				.isPost()
				.build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		System.out.println("filterRequest:"+contents.getTextContents());
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		System.out.println(contents.getTextContents());
		if (contents.isText() && contents.getTextContents().contains("验证码")) {
			return;
		}
		success = true;
		if (contents.isText() && contents.getTextContents().contains("transid\":\"")) {
			checkTel = true;
		}
	}

}
