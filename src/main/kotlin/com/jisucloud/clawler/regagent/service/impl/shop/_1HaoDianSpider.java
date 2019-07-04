package com.jisucloud.clawler.regagent.service.impl.shop;

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
//@UsePapaSpider
public class _1HaoDianSpider implements PapaSpider,AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "1号店(yhd.com)网上超市精选全球好货,提供休闲零食、母婴玩具、进口食品、服饰内衣,1号生鲜、家电家居、手机电脑、宠物用品等各个品类的优质商品。";
	}

	@Override
	public String platform() {
		return "yhd";
	}

	@Override
	public String home() {
		return "yhd.com";
	}

	@Override
	public String platformName() {
		return "1号店";
	}

	@Override
	public String[] tags() {
		return new String[] {"二手购物" , "3C产品"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13800000000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(true, false);
			chromeDriver.addAjaxHook(this);
//			chromeDriver.getIgnoreTimeout("https://www.yhd.com/");
//			chromeDriver.get("https://passport.yhd.com/passport/login_input.do");
			chromeDriver.get("https://passport.yhd.com/m/find_pwd_input.do");
			Thread.sleep(2000);
			chromeDriver.findElementById("login_account").sendKeys(account);
			chromeDriver.findElementById("confirmUser").click();
			Thread.sleep(3000);
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
		return HookTracker.builder().addUrl("ConfirmUserForFindPwd.do").isPOST().build();
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
