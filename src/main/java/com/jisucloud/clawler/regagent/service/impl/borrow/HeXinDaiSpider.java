package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;


import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "hexindai.com", 
		message = "和信贷，纳斯达克上市互联网金融平台。注册资本1亿元，5年稳健运营，AAA级互联网信用认证，央行支付清算协会互联网金融风险信息共享系统接入单位，严谨的风险管控保护体系，为您提供专业、透明的出借服务！", 
		platform = "hexindai", 
		platformName = "和信贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15201215815", "18210538513" },
		excludeMsg = "防火墙")
public class HeXinDaiSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			String url = "https://www.hexindai.com/register";
			chromeDriver.addAjaxHook(this);
			chromeDriver.get(url);
			smartSleep(3000);
			chromeDriver.findElementByCssSelector("input[id=registerPhone]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[id=registerPwd]").click();
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
		return HookTracker.builder().addUrl("check/username/exist").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("existed\":true");
	}

}
