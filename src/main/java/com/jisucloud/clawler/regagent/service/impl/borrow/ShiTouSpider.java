package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class ShiTouSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "石投金融是实投(上海)互联网金融信息服务有限公司旗下的互联网金融理财平台，是国内领先的互联网金融网络借贷平台。";
	}

	@Override
	public String platform() {
		return "shitou";
	}

	@Override
	public String home() {
		return "shitou.com";
	}

	@Override
	public String platformName() {
		return "石投金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			String url = "https://www.shitou.com/login";
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("http://www.baidu.com/link?url=MUS_n9bK5KgnJ1M6Y5VG2gT87k7wXd4ejxSLqLyPy-juNLIbe9TAgzlA5__aqaN5&wd=&eqid=cea493ff0001fbdc000000025cf8c6bf");
			chromeDriver.get(url);
			smartSleep(3000);
			chromeDriver.findElementByCssSelector("input[ng-model='user.loginName']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[ng-model='user.password']").click();
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
		return HookTracker.builder().addUrl("user/loginNameVerification.do").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("code\":\"000000");
	}

}
