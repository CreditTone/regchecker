package com.jisucloud.clawler.regagent.service.impl.music;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "ximalaya.com", 
		message = "喜马拉雅，颠覆了传统电台、个人网络电台单调的在线收听模式，让人们不仅能随时随地，听我想听，更能够轻松创建个人电台，随时分享好声音。在喜马拉雅，你随手就能上传声音作品，创建一个专属于自己的个人电台，持续发展积累粉丝并始终和他们连在一起。", 
		platform = "ximalaya", 
		platformName = "喜马拉雅", 
		tags = { "听书", "生活休闲" }, 
		testTelephones = { "13700982607", "18212345678" })
public class XiMaLaYaSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean isFlag = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://www.ximalaya.com/passport/login");
			smartSleep(2000);
			chromeDriver.findElementById("userAccount").sendKeys(account);
			chromeDriver.findElementById("userPwd").click();
			smartSleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return isFlag;
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
		return HookTracker.builder().addUrl("https://www.ximalaya.com/passport/login/checkAccount").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		isFlag = contents.getTextContents().contains("true");
	}

}
