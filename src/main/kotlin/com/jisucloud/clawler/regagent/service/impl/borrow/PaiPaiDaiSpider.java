package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;

import com.google.common.collect.Sets;

@Slf4j
@UsePapaSpider
public class PaiPaiDaiSpider implements PapaSpider,AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean success = false;

	@Override
	public String message() {
		return "拍拍贷（ppdai.com）美国纽交所上市企业，与招商银行达成存管的平台，专注信用借款和优质出借服务11年，已服务千万用户。轻松借款，额度高，资金链透明，注册资本10亿元。出借风险分散，期限灵活，借款额度高至20万。";
	}

	@Override
	public String platform() {
		return "ppdai";
	}

	@Override
	public String home() {
		return "ppdai.com";
	}

	@Override
	public String platformName() {
		return "拍拍贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910250000", "18210538513");
	}
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(PaiPaiDaiSpider.class);
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				chromeDriver.findElementByCssSelector(".changeCode").click();
				WebElement img = chromeDriver.findElementByCssSelector("#CodeImg");
				Thread.sleep(1000);
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
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://passport.ppdai.com/resetPassword.html");
			Thread.sleep(3000);
			chromeDriver.findElementById("inputName1").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				WebElement rapid = chromeDriver.findElementByCssSelector("#inputCode1");
				rapid.clear();
				String vcode = getImgCode();
				chromeDriver.jsInput(rapid, vcode);
				Thread.sleep(1000);
				chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("#validateBtn1"));
				Thread.sleep(2000);
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
				.isPOST()
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
