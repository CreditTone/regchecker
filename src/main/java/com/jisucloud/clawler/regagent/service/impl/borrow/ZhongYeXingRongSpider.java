package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Random;

@Slf4j
@PapaSpiderConfig(
		home = "zyxr.com", 
		message = "中业兴融(zyxr.com)是专业可信赖的P2P网贷平台,拥有超过100万注册用户,为用户提供专业透明的投资渠道和优质先进的科技金融服务,用户可通过平台进行定期投资获得稳定。", 
		platform = "zyxr", 
		platformName = "中业兴融", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15201215815", "18212345678" },
		exclude = true)
public class ZhongYeXingRongSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector(".form-control img");
				img.click();smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "ne5");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newAndroidInstance(false, true);
			chromeDriver.get("https://www.zyxr.com/");
			chromeDriver.get("https://www.zyxr.com/wap/?chl=bd-keyword-wap#/login?backUrl=https%3A%2F%2Fwww.zyxr.com%2Fwap%2F%3Fchl%3Dbd-keyword-wap%23%2Flogin");smartSleep(3000);
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementByCssSelector("input[type='tel']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[type='password']").sendKeys("x19cn10xn" + new Random().nextInt(100000));
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementByCssSelector("input[placeholder='请输入图形验证码']");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.findElementById("button[class='btn btn-primary btn-block']").click();
				smartSleep(3000);
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

	@Override
	public HookTracker getHookTracker() {
		return HookTracker.builder().addUrl("https://www.zyxr.com/UserWeb/login.json").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String content = contents.getTextContents();
		if (!content.contains("验证码错误")) {
			vcodeSuc = true;
			checkTel = content.contains("密码错误") || content.contains("锁定");
		}
	}

}
