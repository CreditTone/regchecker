package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class XinRongSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean success = false;

	@Override
	public String message() {
		return "信融财富是一家专业提供网络借贷信息服务中介平台,平台运营时间超过六年,获得上市系融资。平台致力于为融资方高效解决资金需求,为出借方提供更便捷的投资服务。";
	}

	@Override
	public String platform() {
		return "xinrong";
	}

	@Override
	public String home() {
		return "xinrong.com";
	}

	@Override
	public String platformName() {
		return "信融财富";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910250000", "18210538513");
	}
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(XinRongSpider.class);
	}
	
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

	@Override
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
		System.out.println(contents.getTextContents());
		if (contents.isText() && contents.getTextContents().contains("验证码")) {
			return;
		}
		success = true;
		if (contents.isText() && contents.getTextContents().contains("9999")) {
			checkTel = true;
		}
	}

}
