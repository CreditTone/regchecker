package com.jisucloud.clawler.regagent.service.impl.law;

import com.deep007.spiderbase.util.StringUtil;
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
		home = "findlaw.cn", 
		message = "遇事找法,从找法网开始!15年法律咨询领导品牌,3164万需求者寻求帮助、在线找律师的第一站。找法网拥有注册律师近17万、年解答咨询量超过400万、年代理案件超20万。 ", 
		platform = "findlaw", 
		platformName = "找法网", 
		tags = { "律师", "法律" ,"打官司" }, 
		testTelephones = { "13991808887", "18212345678" })
public class ZhaoFaWangSpider extends PapaSpider implements AjaxHook {
	
	public boolean checkTelephone(String account) {
		ChromeAjaxHookDriver chromeDriver = null;
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("http://uc.findlaw.cn/lawyer/");
			smartSleep(2000);
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("mobilep").sendKeys(account);
			chromeDriver.findElementById("uname").click();
			smartSleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkResult;
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
		return HookTracker.builder().addUrl("http://uc.findlaw.cn/index.php?c=Ajax&a=ajaxDispatcher").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	private boolean checkResult = false;
	
	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String res = StringUtil.unicodeToString(contents.getTextContents());
		checkResult = res.contains("已被使用");
	}

}
