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

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "vip.com", 
		message = "唯品会vip购物网以1-7折超低折扣对全球各大品牌进行限时特卖,商品囊括服装、化妆品、家居、奢侈品等上千品牌。100%正品、低价、货到付款、7天无理由退货。", 
		platform = "vip", 
		platformName = "唯品会", 
		tags = {"化妆品" , "奢侈品" ,"购物" }, 
		testTelephones = {"13910250000", "18210538513" },
		excludeMsg = "按钮无法点击")
public class WeiPinHuiSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean success = false;
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("img.captcha-img");
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
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(false, false);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://mlogin.vip.com/asserts/password/recovered.html?userName=&mars_cid=1561281610453_c9b31eba9340ca92a42b8482c2c0b689&domainName=m.vip.com");smartSleep(2000);
			Actions actions = new Actions(chromeDriver);
			actions.sendKeys(Keys.F12).perform();smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[class='form-input J-user-phone']").sendKeys(account);
			chromeDriver.mouseClick(chromeDriver.findElementByLinkText("下一步"));smartSleep(3000);
			for (int i = 0; i < 5; i++) {
				if (chromeDriver.checkElement(".m-captcha-wrap")) {
					String vcode = getImgCode();
					WebElement rapid = chromeDriver.findElementByCssSelector("#J-captcha");
					rapid.clear();
					rapid.sendKeys(vcode);
					chromeDriver.mouseClick(chromeDriver.findElementByCssSelector(".mg-dialog-foot button[data-index='1']"));smartSleep(1000);
					if (chromeDriver.checkElement("#J-captcha-error") && chromeDriver.findElementById("J-captcha-error").getText().contains("验证码")) {
						continue;
					}
				}smartSleep(2000);
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
				.addUrl("asserts/password/phone.html?")
				.addUrl("ajaxapi-forget.html")
				.build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (messageInfo.getOriginalUrl().contains("https://mlogin.vip.com/ajaxapi-forget.html?")) {
			return DEFAULT_HTTPRESPONSE;
		}
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		success = true;
		checkTel = true;
	}

}
