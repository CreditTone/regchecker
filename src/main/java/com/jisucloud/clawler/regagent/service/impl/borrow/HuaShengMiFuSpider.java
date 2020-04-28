package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;


import java.util.Map;


import org.openqa.selenium.WebElement;

@Slf4j
@PapaSpiderConfig(
		home = "yaoyuefu.com", 
		message = "花生米富官方网站,作为新一代互联网金融平台,在健全的风险管控体系、银行存管、三级等保基础上,提供多重风控、真实债权、消费场景等信息服务。为您的出借保驾护航!", 
		platform = "yaoyuefu", 
		platformName = "花生米富", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "13910252000", "18212345678" })
public class HuaShengMiFuSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				chromeDriver.findElementByCssSelector("#getcode_num p").click();smartSleep(1000);
				WebElement img = chromeDriver.findElementByCssSelector("#show_code");
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.addAjaxHook(this);
			String url = "https://www.yaoyuefu.com/default/login";
			chromeDriver.get(url);smartSleep(3000);
			chromeDriver.findElementByCssSelector("#mobile").sendKeys(account);
			chromeDriver.findElementByCssSelector("#password").sendKeys("xas2130sxcna");
			for (int i = 0; i < 5; i++) {
				String vcode = getImgCode();
				WebElement token = chromeDriver.findElementById("p_token");
				token.clear();
				token.sendKeys(vcode);
				chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("#login"));smartSleep(3000);
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
		return HookTracker.builder().addUrl("default/user_login").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		if (!contents.getTextContents().contains("验证码错误")) {
			vcodeSuc = true;
			checkTel = contents.getTextContents().contains("10013");
		}
	}

}
