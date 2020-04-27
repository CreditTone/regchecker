package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "p2phx.com", 
		message = "恒信易贷,一家专注车贷的网络借贷信息中介平台,获优选资本2亿元B轮融资,股东背景实力雄厚。恒信易贷坚持“更合规的网贷平台”的定位,致力于为投融资需求者提供。", 
		platform = "p2phx", 
		platformName = "恒信易贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13912345678", "18212345678" })
public class HengXinYiDaiSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#captchaImage");
				img.click();smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "n4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}



	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newAndroidInstance(false, true);
			chromeDriver.get("https://wap.p2phx.com/main/login");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("username").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("lvnqwnk12mcxn");
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("captcha");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.findElementById("submit").click();smartSleep(3000);
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
		return HookTracker.builder().addUrl("main/login/submit").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (!contents.getTextContents().contains("验证码错误")) {
			vcodeSuc = true;
			checkTel = contents.getTextContents().contains("锁定");
		}
	}

}
