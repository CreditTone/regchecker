package com.jisucloud.clawler.regagent.service.impl.news;

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
		home = "sohu.com", 
		message = "搜狐公司是中国的新媒体、通信及移动增值服务公司，是互联网品牌。搜狐是一个新闻中心、联动娱乐市场，跨界经营的娱乐中心、体育中心、时尚文化中心。搜狐公司是2008北京奥运会互联网内容服务赞助商。", 
		platform = "sohunews", 
		platformName = "搜狐", 
		tags = { "新闻资讯" }, 
		testTelephones = { "18515290717", "18212345678" })
public class SouHuNewsSpider extends PapaSpider implements AjaxHook {

	public boolean checkTelephone(String account) {
		ChromeAjaxHookDriver chromeDriver = null;
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithRandomProxy(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://v4.passport.sohu.com/fe/login");
			smartSleep(5000);
			chromeDriver.addAjaxHook(this);
//			chromeDriver.findElementByLinkText("登录狐友").click();
//			smartSleep(1000);
//			chromeDriver.findElementByCssSelector("li[data-role='userlogin-bar']").click();
//			smartSleep(1000);
			chromeDriver.findElementByCssSelector(".input-content input[type='text']").sendKeys(account);
			chromeDriver.findElementByCssSelector(".password-box input[type='password']").sendKeys("xa2109d990das");
			chromeDriver.findElementByCssSelector(".login-button span").click();
			smartSleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return check;
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
		return HookTracker.builder().addUrl("https://v4.passport.sohu.com/i/login").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	private boolean check = false;
	
	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		check = contents.getTextContents().contains("password");
	}

}
