package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Slf4j
@UsePapaSpider
public class UCSpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "UC浏览器是全球6亿用户共同选择的智能手机浏览器,登陆UC官网免费下载UC浏览器安卓版/iPhone版,给您超快感的上网体验。";
	}

	@Override
	public String platform() {
		return "uc";
	}

	@Override
	public String home() {
		return "uc.cn";
	}

	@Override
	public String platformName() {
		return "UC浏览器";
	}

	@Override
	public String[] tags() {
		return new String[] {"工具", "浏览器"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new UCSpider().checkTelephone("18210538513"));
//		System.out.println(new UCSpider().checkTelephone("18210530000"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#captchaImg img");
				img.click();
				Thread.sleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			chromeDriver.get("https://api.open.uc.cn/cas/forgotpassword/forgotPasswordCommit?client_id=4&redirect_uri=http%3A%2F%2Fid.uc.cn%2F&display=pc&change_uid=0");
			Thread.sleep(2000);
			WebElement nameInputArea = chromeDriver.findElementByCssSelector("#loginName");
			nameInputArea.sendKeys(account);
			for (int i = 0 ; i < 5 ; i ++) {
				WebElement captcha = chromeDriver.findElementByCssSelector("#captchaVal");
				captcha.clear();
				captcha.sendKeys(getImgCode());
				chromeDriver.reInject();
				chromeDriver.findElementByCssSelector("#submit_btn").click();
				Thread.sleep(3000);
				if (chromeDriver.checkElement("#dForm2")) {
					return true;
				}
				String dCaptchaValError = chromeDriver.findElementById("dCaptchaValError").getText();
				if (dCaptchaValError.contains("错误")){
					continue;
				}
				break;
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

}
