package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.clawler.regagent.util.StringUtil;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "xiaojilicai.com", 
		message = "小鸡理财是一家专注于小微企业信贷的网络借贷信息中介服务平台,自2014年上线以来,一直致力于向借款人和出借人提供信息搜集、信息公布、信息交互、借贷撮合等优质服务。", 
		platform = "xiaojilicai", 
		platformName = "小鸡理财", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15900068904", "18210538513" })
public class XiaoJinLiCaiSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#verifycode");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "n4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	String code;


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.get("https://cg.xiaojilicai.com/Member/Register/login");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("username").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("as213asd1");
			for (int i = 0; i < 5; i++) {
				code = getImgCode();
				WebElement authcode = chromeDriver.findElementById("authcode");
				authcode.clear();
				authcode.sendKeys(code);
				chromeDriver.findElementById("login-btn").click();smartSleep(3000);
				if (vcodeSuc) {
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
	
	HookTracker hookTracker = HookTracker.builder()
			.addUrl("doLogin")
			.isPost().build();

	@Override
	public HookTracker getHookTracker() {
		// TODO Auto-generated method stub
		return hookTracker;
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String res = StringUtil.unicodeToString(contents.getTextContents());
		if (!res.contains("验证码错误")) {
			vcodeSuc = true;
			checkTel = res.contains("密码");
		}
	}

}
