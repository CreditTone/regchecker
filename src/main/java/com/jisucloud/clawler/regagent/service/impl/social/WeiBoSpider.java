package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep007.spiderbase.util.StringUtil;
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
public class WeiBoSpider extends PapaSpider implements AjaxHook {

	@Override
	public String message() {
		return "微博（Weibo）是指一种基于用户关系信息分享、传播以及获取的通过关注机制分享简短实时信息的广播式的社交媒体、网络平台，用户可以通过PC、手机等多种移动终端接入，以文字、图片、视频等多媒体形式，实现信息的即时分享、传播互动。";
	}

	@Override
	public String platform() {
		return "weibo";
	}

	@Override
	public String home() {
		return "weibo.com";
	}

	@Override
	public String platformName() {
		return "微博";
	}

	@Override
	public String[] tags() {
		return new String[] {"泛社交" , "微博"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18700001101", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		ChromeAjaxHookDriver chromeDriver = null;
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://weibo.com/signup/signup.php");
			smartSleep(2000);
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementByCssSelector("input[name='username']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[name='passwd']").click();
			smartSleep(2000);
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
		return HookTracker.builder().addUrl("https://weibo.com/signup/v5/formcheck").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	
	private boolean checkTel = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = StringUtil.unicodeToString(contents.getTextContents()).contains("已注册");
	}

}
