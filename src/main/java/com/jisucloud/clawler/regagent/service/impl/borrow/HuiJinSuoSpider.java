package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "hfax.com", 
		message = "惠金所Hfax(北京中关村融汇金融信息服务有限公司)成立于2015年4月，是阳光保险旗下的互联网金融信息服务平台。惠金所定位于做有影响力的个人财富管理及中小微企业融资服务。", 
		platform = "hfax", 
		platformName = "惠金所", 
		tags = { "P2P", "借贷" , "小微金融" }, 
		testTelephones = { "15985268900", "18212345678" })
public class HuiJinSuoSpider extends PapaSpider implements AjaxHook {

	public boolean checkTelephone(String account) {
		ChromeAjaxHookDriver chromeAjaxHookDriver = null;
		try {
			chromeAjaxHookDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeAjaxHookDriver.get("https://www.hfax.com/login.html#/?rsrc=https%3A%2F%2Fwww.hfax.com%2Fpub-module.html%23%2Fpwd-reset%3F");
			chromeAjaxHookDriver.addAjaxHook(this);
			smartSleep(2000);
			chromeAjaxHookDriver.findElementById("userName").sendKeys(account);
			chromeAjaxHookDriver.findElementById("passWord").sendKeys("dsa210asddas9");
			for (int i = 0; i < 5; i++) {
				chromeAjaxHookDriver.findElementByCssSelector("div[class='gradient login-btn login-btn']").click();
				smartSleep(2000);
				if (check) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeAjaxHookDriver != null) {
				chromeAjaxHookDriver.quit();
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
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("https://www.hfax.com/pc-api/user/login").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	
	boolean check = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String res =  contents.getTextContents();
		check = res.contains("将被锁定") || res.contains("510003") || res.contains("已冻结");
	}

}
