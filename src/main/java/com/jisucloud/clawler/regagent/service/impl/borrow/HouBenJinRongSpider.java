package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;


import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "houbank.com", 
		message = "厚本金融(houbank.com)是专业的互联网借贷撮合平台,为出借人和借款人提供优质的互联网金融借贷信息中介服务,中华财险保证保险逐步覆盖平台资产。", 
		platform = "houbank", 
		platformName = "厚本金融", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13912345678", "18212345678" })
public class HouBenJinRongSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, false);
			chromeDriver.get("http://www.baidu.com/link?url=dfL9ZPOGYGBiMGQ2RqQdw810qctmkrOMZm_IZoVE9VbbL9E1a1GOvQZhr2Xn0rbM&wd=&eqid=802dd3a500059a35000000025d061f5a");
			chromeDriver.get("https://www.houbank.com/pro/account/forget");
			chromeDriver.addAjaxHook(this);
			smartSleep(1000);
			chromeDriver.jsInput(chromeDriver.findElementByCssSelector("input[placeholder='请输入手机号']"), account);
			this.account = account;
			chromeDriver.jsInput(chromeDriver.findElementByCssSelector("input[placeholder='请输入图形验证码']"), "3579");
			chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("input[class='step__btn']"));
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
		return HookTracker.builder().addUrl("user/checkImageCode").isPost().build();
	}
	
	String account = "";

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		contents.setTextContents("{\"imgCod\":\"2586\",\"mobile\":\""+account+"\"}");
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		System.out.println(contents.getTextContents());
		if (!contents.getTextContents().contains("验证码错误")) {
			vcodeSuc = true;
			checkTel = contents.getTextContents().contains("图片验证码校验成功");
		}
	}

}
