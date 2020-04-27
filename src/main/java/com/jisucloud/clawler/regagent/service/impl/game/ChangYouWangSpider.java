package com.jisucloud.clawler.regagent.service.impl.game;

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
		home = "changyou.com", 
		message = "畅游有限公司(纳斯达克股票交易代码:CYOU),中国在线游戏开发和运营商之一,自主研发的《新天龙八部》是中国最受欢迎的大型多人在线角色扮演游戏之一。", 
		platform = "changyou", 
		platformName = "畅游网", 
		tags = { "游戏" }, 
		testTelephones = { "15700102865", "18212345678" })
public class ChangYouWangSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			String url = "http://zhuce.changyou.com/reg.act?gameType=PE-ZHPT&invitecode=&regWay=phone&suffix=";
			chromeDriver.get(url);
			smartSleep(3000);
			chromeDriver.findElementById("securityPhone").sendKeys(account);
			chromeDriver.findElementById("passwd_phone").click();
			smartSleep(3000);
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
		return HookTracker.builder().addUrl("baseReg/checkCnIsUsed.act").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("used");
	}

}
