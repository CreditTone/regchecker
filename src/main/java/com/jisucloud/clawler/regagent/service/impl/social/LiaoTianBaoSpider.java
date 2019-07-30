package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class LiaoTianBaoSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "聊天宝，原名子弹短信，最大的特色就是可以通过聊天、购物、游戏或完成任务赚取金币，随后可以通过金币按一定比例转换为现金，不过，现金余额超过30元才支持提现。";
	}

	@Override
	public String platform() {
		return "zidanduanxin";
	}

	@Override
	public String home() {
		return "zidanduanxin.com";
	}

	@Override
	public String platformName() {
		return "聊天宝";
	}

	@Override
	public String[] tags() {
		return new String[] { "社交", "资讯" };
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210530000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			String url = "https://im.zidanduanxin.com/login";
			chromeDriver.get(url);
			smartSleep(2000);
			chromeDriver.findElementByLinkText("使用密码登录").click();
			smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[placeholder='手机号']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[placeholder='密码']").sendKeys("xoax2mxcndn");
			chromeDriver.findElementByCssSelector("div.btn").click();
			smartSleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		return HookTracker.builder().addUrl("im/tokens").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("65");
	}

}
