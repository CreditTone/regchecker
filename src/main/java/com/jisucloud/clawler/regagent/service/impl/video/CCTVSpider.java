package com.jisucloud.clawler.regagent.service.impl.video;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.impl.life.IPandaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


import org.openqa.selenium.WebElement;

@Slf4j
@PapaSpiderConfig(
		home = "cctv.com", 
		message = "央视网(www.cctv.com)由中央广播电视总台主办,是以视频为特色的中央重点新闻网站,是央视的融合传播平台,是拥有全牌照业务资质的大型互联网文化企业。", 
		platform = "cctv", 
		platformName = "央视网", 
		tags = { "影音", "直播", "视频" }, 
		testTelephones = { "13925306960", "18212345678" })
public class CCTVSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean checkSuc = false;

	public boolean checkTelephone(String account) {
//		try {
//			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
//			chromeDriver.get("http://reg.cctv.com/forgetPassword/findPassword.action");
//			chromeDriver.addAjaxHook(this);
//			chromeDriver.findElementByCssSelector("#loginName").sendKeys(account);smartSleep(500);
//			for (int i = 0; i < 5; i++) {
//				String imcode = getImgCode();
//				WebElement nameInputArea = chromeDriver.findElementByCssSelector("#verificationCode");
//				nameInputArea.sendKeys(imcode);
//				chromeDriver.findElementByLinkText("下一步").click();smartSleep(2000);
//				if (checkSuc) {
//					break;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			if (chromeDriver != null) {
//				chromeDriver.quit();
//			}
//		}
//		return checkTel;
		return new IPandaSpider().checkTelephone(account);
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
		return HookTracker.builder().addUrl("forgetPassword/checkLoginName.action").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (!contents.getTextContents().contains("验证码不正确")) {
			checkSuc = true;
			JSONObject result = JSON.parseObject(contents.getTextContents());
			checkTel = result.getIntValue("userSeqId") > 0;
		}
	}

}
