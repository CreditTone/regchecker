package com.jisucloud.clawler.regagent.service.impl.health;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "yihu.com", 
		message = "医护网是健康之路公司旗下的网上预约挂号平台，提供全国200多家三甲医院知名专家的就医指导及门诊预约挂号服务。", 
		platform = "yihuw", 
		platformName = "医护网", 
		tags = { "购药", "用药", "在线医生" }, 
		testTelephones = { "15120058878", "18212345678"},
				exclude = true, excludeMsg = "安全控件问题")
public class YiHuWangSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	
	private boolean checkTelephone = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.yihu.com/User/login.shtml");
			chromeDriver.addAjaxHook(this);
			smartSleep(1000);
			chromeDriver.keyboardInput(chromeDriver.findElementByCssSelector("#txtLoginid"), account);
			chromeDriver.keyboardInput(chromeDriver.findElementById("txtPassword"), "asda01h31c");
			chromeDriver.mouseClick(chromeDriver.findElementById("loginButton"));
			smartSleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTelephone;
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
		return HookTracker.builder().addUrl("https://www.yihu.com/User/doLogin").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTelephone = contents.getTextContents().contains("-9999");
	}

}
