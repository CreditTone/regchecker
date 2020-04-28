package com.jisucloud.clawler.regagent.service.impl._3c;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;

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
		home = "cnmo.com", 
		message = "手机中国是一个实现了专业、时尚、品位并重的新兴手机媒体。相对于传统手机媒体不同之处在于，手机中国不仅提供指导消费、倡导应用，同时还在引领着手机的时尚与品位。在专业端，手机中国提供售前指导，包括价格、选购、评测试用、新品消息等，同时还在时尚与品味端，提供全方位的服务。", 
		platform = "cnmo", 
		platformName = "手机中国", 
		tags = { "手机" , "媒体" , "评测" }, 
		testTelephones = { "15985268900", "18212345678" }
		)
public class ShouJiZhongGuoSpider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("http://passport.cnmo.com/register/?backurl=http://www.cnmo.com/");
			chromeDriver.keyboardInput(chromeDriver.findElementByCssSelector("#m_mobile"), account);
			chromeDriver.findElementById("m_uname").click();
			smartSleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return ct;
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
		return HookTracker.builder().addUrl("http://passport.cnmo.com/index.php").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	
	boolean ct = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		ct = contents.getTextContents().contains("3");
	}

}
