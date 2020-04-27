package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

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
		home = "xiaoniu88.com", 
		message = "小牛在线是中国线上智能金融服务平台,提供专业的P2P网贷服务,为出借人提供多种互联网金融服务项目,是中国社科院网贷评级A级平台,互联网金融平台首选小牛在线!", 
		platform = "xiaoniu88", 
		platformName = "小牛在线", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13912345678", "18212345678" })
public class XiaoNiuZaiXianSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#step1-img-code");
				img.click();smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "ne6");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.get("https://www.xiaoniu88.com/user/forgetpassword");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("username").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("randomCode");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.findElementById("step1-btn").click();smartSleep(3000);
				if (vcodeSuc) {
					break;
				}
				if (chromeDriver.getCurrentUrl().contains("user/forgetpassword/step1")) {
					return true;
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

	@Override
	public HookTracker getHookTracker() {
		return HookTracker.builder().addUrl("https://www.xiaoniu88.com/user/forgetpassword/verifycode").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String text = contents.getTextContents();
		vcodeSuc =  text.contains("0") || text.contains("9");
		checkTel = text.contains("0");
	}

}
