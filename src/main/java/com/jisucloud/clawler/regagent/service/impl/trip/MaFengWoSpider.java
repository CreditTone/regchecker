package com.jisucloud.clawler.regagent.service.impl.trip;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class MaFengWoSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "马蜂窝!靠谱的旅游攻略,自由行,自助游分享社区,海量旅游景点图片、游记、交通、美食、购物等自由行旅游攻略信息,马蜂窝旅游网获取自由行,自助游攻略信息更全面。";
	}

	@Override
	public String platform() {
		return "mafengwo";
	}

	@Override
	public String home() {
		return "mafengwo.com";
	}

	@Override
	public String platformName() {
		return "马蜂窝";
	}

	@Override
	public String[] tags() {
		return new String[] {"旅游" , "酒店" , "美食" , "o2o"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13800100001", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://passport.mafengwo.cn/regist-mobile.html");smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[name='passport']").sendKeys(account);
			chromeDriver.findElementByCssSelector("button[type='submit']").click();smartSleep(3000);
			if (chromeDriver.checkElement("div[class='alert alert-danger']")) {
				return true;
			}
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
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("ConfirmUserForFindPwd.do").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		System.out.println(contents.getTextContents());
		return null;
	}
	
	boolean checkTel = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("****");
	}

}
