package com.jisucloud.clawler.regagent.service.impl.life;

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


@Slf4j
@PapaSpiderConfig(
		home = "mtime.com", 
		message = "Mtime时光网,中国最专业的电影电视剧及影人资料库,这里有最新最专业的电影新闻,预告片,海报,写真和热门影评,同时提供电影院影讯查询,博客,相册和群组等服务,是电影粉丝的最佳电影社区。", 
		platform = "mtime", 
		platformName = "Mtime时光网", 
		tags = { "娱乐", "影音", "追星" }, 
		testTelephones = { "13912345678", "18212345678" })
public class MtimeSpider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, false);
			chromeDriver.get("https://passport.mtime.com/member/signin/?redirectUrl=http%3A%2F%2Fwww.mtime.com%2F");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("reg_mobile").sendKeys(account);
			chromeDriver.findElementById("reg_password").click();
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
		return HookTracker.builder().addUrl("api/check_unique_loginname?").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		try {
			JSONObject result = JSON.parseObject(contents.getTextContents());
			checkTel = result.getBooleanValue("exist");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
