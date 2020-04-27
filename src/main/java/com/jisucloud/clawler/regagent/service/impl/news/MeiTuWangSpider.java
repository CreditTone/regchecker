package com.jisucloud.clawler.regagent.service.impl.news;

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
		home = "meitu.com", 
		message = "美图官网,提供美图手机(美图T9、美图V6、美图M8s、美图T8s)、相关配件的详细介绍及在线购买。同时也是美图秀秀、美颜相机、美拍等热门产品的官方网站。", 
		platform = "meitu", 
		platformName = "美图秀秀", 
		userActiveness = 0.7f,
		tags = { "美图" , "美颜", "工具" }, 
		testTelephones = { "18212345678", "15161509916" })
public class MeiTuWangSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://account.meitu.com/#!/login/");
			chromeDriver.addAjaxHook(this);smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[placeholder='请输入手机号码']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[type='password']").sendKeys("xas1231sad");
			chromeDriver.findElementByCssSelector("button[class='Button submit form-submit']").click();smartSleep(3000);
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
		return HookTracker.builder().addUrl("oauth/access_token.json").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	boolean checkTel;
	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = !contents.getTextContents().contains("还未被注册");
	}

}
