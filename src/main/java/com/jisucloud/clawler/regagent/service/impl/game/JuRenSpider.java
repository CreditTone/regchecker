package com.jisucloud.clawler.regagent.service.impl.game;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;

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
		home = "ztgame.com", 
		message = "巨人网络致力于不断为玩家提供民族精品网游,旗下拥有《征途》、《征途2》、《巨人》、《绿色征途》、《万王之王3》、《艾尔之光》、《仙途》、《巫师之怒》。", 
		platform = "ztgame", 
		platformName = "巨人网络", 
		tags = { "游戏" }, 
		testTelephones = { "15700102865", "18212345678" })
public class JuRenSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newAndroidInstance(true, true);
			String url = "https://reg.ztgame.com/mobile";
			chromeDriver.addAjaxHook(this);
			chromeDriver.get(url);smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[name=phone]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[name=get_mpcode]").click();smartSleep(2000);
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
		return HookTracker.builder().addUrl("common/query?").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (messageInfo.getOriginalUrl().contains("common/sendmpcode")) {
			return DEFAULT_HTTPRESPONSE;
		}
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("已存在");
	}

}
