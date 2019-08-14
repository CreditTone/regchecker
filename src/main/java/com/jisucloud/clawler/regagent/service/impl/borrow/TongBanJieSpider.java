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
		home = "tongbanjie.com", 
		message = "铜板街(tongbanjie.com)成立于2012年9月12日,是国内领先的互联网金融信息服务提供商,接入互联网金融风险信息共享系统,中国互联网金融协会成员,AAA级信用企业。", 
		platform = "tongbanjie", 
		platformName = "铜板街", 
		tags = {"P2P", "借贷"}, 
		testTelephones = {"15900068904", "18210538513" },
		exclude = true)
public class TongBanJieSpider extends PapaSpider implements AjaxHook{

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
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://login.tongbanjie.com/web/login.html?redirectURL=https://login.tongbanjie.com/web/login.html");
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
