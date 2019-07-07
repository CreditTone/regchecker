package com.jisucloud.clawler.regagent.service.impl.news;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class MeiTuWangSpider implements PapaSpider,AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "美图官网,提供美图手机(美图T9、美图V6、美图M8s、美图T8s)、相关配件的详细介绍及在线购买。同时也是美图秀秀、美颜相机、美拍等热门产品的官方网站。";
	}

	@Override
	public String platform() {
		return "meitu";
	}

	@Override
	public String home() {
		return "meitu.com";
	}

	@Override
	public String platformName() {
		return "美图秀秀";
	}

	@Override
	public String[] tags() {
		return new String[] {"美图" , "美颜", "工具"};
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, false);
			chromeDriver.get("https://account.meitu.com/#!/login/");
			chromeDriver.addAjaxHook(this);
			Thread.sleep(2000);
			chromeDriver.findElementByCssSelector("input[placeholder='请输入手机号码']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[type='password']").sendKeys("xas1231sad");
			chromeDriver.findElementByCssSelector("button[class='Button submit form-submit']").click();
			Thread.sleep(3000);
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
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15161509916");
	}

	@Override
	public HookTracker getHookTracker() {
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("oauth/access_token.json").isPOST().build();
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
