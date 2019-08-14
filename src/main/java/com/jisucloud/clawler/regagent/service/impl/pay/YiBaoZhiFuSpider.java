package com.jisucloud.clawler.regagent.service.impl.pay;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
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
		home = "yeepay.com", 
		message = "易宝支付是中国支付行业的开创者和领导者，也是互联网金融（ITFIN）和移动互联领军企业。易宝于2003年8月成立，总部位于北京，现有员工逾千人。", 
		platform = "yeepay", 
		platformName = "易宝支付", 
		tags = { "聚合支付" , "互联网金融" }, 
		testTelephones = { "13193091202", "18210538513" })
public class YiBaoZhiFuSpider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#picCode");
				img.click();
				smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "n4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://mp.yeepay.com/app/merchantUserManagement/getBack/loginPwd");
			smartSleep(2000);
			chromeDriver.findElementByCssSelector("#userName").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				if (chromeDriver.checkElement("#validCode")) {
					String imageCode = getImgCode();
					WebElement mark = chromeDriver.findElementByCssSelector("#validCode");
					mark.clear();
					mark.sendKeys(imageCode);
				}
				chromeDriver.findElementByCssSelector("#submitConfirmButton").click();
				smartSleep(2000);
				if (vs) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return ct;
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
		return HookTracker.builder().addUrl("https://mp.yeepay.com/app/merchantUserManagement/getBack/validLoginName").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	
	boolean vs = false;
	boolean ct = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		vs = true;
		ct = contents.getTextContents().contains("member_mobile");
	}

}
