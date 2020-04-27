package com.jisucloud.clawler.regagent.service.impl.borrow;

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
		home = "hoomxb.com", 
		message = "红小宝专业互联网金融平台,银行存管,注册资金2亿元,近12个月平均年化收益11.9%,1000元起投,智能风控,交易过程公开透明,为您的资金保驾护航!", 
		platform = "hoomxb", 
		platformName = "红小宝金融", 
		tags = { "消费分期" , "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15161509916" })
public class HongXiaoBaoSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.hoomxb.com/forgot");
			chromeDriver.addAjaxHook(this);smartSleep(2000);
			chromeDriver.findElementByCssSelector("#J_mobile").sendKeys(account);
			chromeDriver.findElementByCssSelector("#J_captcha").click();smartSleep(3000);
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
		return HookTracker.builder().addUrl("checkExistMobile").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	boolean checkTel;
	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("true");
	}

}
