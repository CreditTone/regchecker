package com.jisucloud.clawler.regagent.service.impl.video;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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


import org.openqa.selenium.WebElement;

@Slf4j
@PapaSpiderConfig(
		home = "iqiyi.com", 
		message = "爱奇艺是由龚宇于2010年4月22日创立的视频网站，2011年11月26日启动“爱奇艺”品牌并推出全新标志。爱奇艺成立伊始，坚持“悦享品质”的公司理念，以“用户体验..", 
		platform = "iqiyi", 
		platformName = "爱奇艺", 
		tags = { "影音", "视频", "MV" }, 
		testTelephones = { "18210530000", "18210538513" })
public class AiQiyiSpider extends PapaSpider implements AjaxHook{
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("http://www.iqiyi.com/iframe/loginreg?ver=1");smartSleep(1000);
			chromeDriver.findElementByLinkText("其他方式登录").click();smartSleep(500);
			chromeDriver.findElementByCssSelector("a[class='other-way-item duanxin']").click();smartSleep(500);
			WebElement nameInputArea = chromeDriver.findElementByCssSelector("input[data-regbox='name']");
			nameInputArea.sendKeys(account);
			chromeDriver.findElementByLinkText("下一步").click();smartSleep(2000);
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
		return HookTracker.builder().addUrl("user/check_account.action").addUrl("secure_send_cellphone_authcode").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		if (messageInfo.getOriginalUrl().contains("secure_send_cellphone_authcode")) {
			return DEFAULT_HTTPRESPONSE;
		}
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (messageInfo.getOriginalUrl().contains("check_account")) {
			try {
				JSONObject rsJsonObject = JSON.parseObject(contents.getTextContents());
				checkTel = rsJsonObject.getBooleanValue("data");
			} catch (Exception e) {
			}
		}
	}

}
