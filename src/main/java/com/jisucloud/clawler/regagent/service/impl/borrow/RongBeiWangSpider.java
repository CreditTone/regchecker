package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

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
		home = "irongbei.com", 
		message = "融贝网是一家优质、透明、普惠的网络借贷信息中介平台,发挥互联网平台高效、便捷的优势,筛选合格出借人与优质借款人,进行撮合服务,构建两者之间的投融资桥梁。", 
		platform = "irongbei", 
		platformName = "融贝网", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class RongBeiWangSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.irongbei.com/userRegister/index");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("mobile").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("captchapic");
				validate.click();
				smartSleep(3000);
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
		return HookTracker.builder().addUrl("userRegister/checkMobileAll").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		vcodeSuc = true;
		checkTel = contents.getTextContents().contains("已被注册") || contents.getTextContents().contains("锁定");
	}

}
