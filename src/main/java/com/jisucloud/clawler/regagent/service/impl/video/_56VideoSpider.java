package com.jisucloud.clawler.regagent.service.impl.video;

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
		home = "56.com", 
		message = "56网是中国原创视频网站,免费上传搞笑逗趣生活视频,观看优质丰富的特色节目,关注感兴趣的原创导演和美女解说,快速分享及评论互动。", 
		platform = "56", 
		platformName = "56视频", 
		tags = { "影音", "视频", "MV" }, 
		testTelephones = { "18515290717", "18210530000" })
public class _56VideoSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean check = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://about.56.com/contactus.html");
			chromeDriver.get("https://www.56.com/");
			smartSleep(1000);
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementByLinkText("注册").click();
			smartSleep(2000);
			chromeDriver.findElementByCssSelector("#regMobileAccount").sendKeys(account);
			chromeDriver.findElementById("regPassWordA").click();
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
		return HookTracker.builder().addUrl("https://v4-passport.56.com/i/verify/mobile/bind").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		check = contents.getTextContents().contains("already has bind");
	}

}
