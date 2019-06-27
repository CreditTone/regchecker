package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;

import java.util.Map;

@Slf4j
//@UsePapaSpider  行为分析
public class LinkedinSpider implements PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "领英,在全球领先职业社交平台查看英领的职业档案。英领的职业档案列出了教育经历。查看英领的完整档案,结识职场人脉和查看相似公司的职位。";
	}

	@Override
	public String platform() {
		return "linkedin";
	}

	@Override
	public String home() {
		return "linkedin.com";
	}

	@Override
	public String platformName() {
		return "领英";
	}

	@Override
	public String[] tags() {
		return new String[] {"招聘" , "职场" , "人脉"};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new LinkedinSpider().checkTelephone("13910252045"));
		System.out.println(new LinkedinSpider().checkTelephone("18210538513"));
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imgObj");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	String code;

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithRandomProxy(true, false, null);
			chromeDriver.get("https://www.linkedin.com/uas/request-password-reset?trk=guest_homepage-basic_forgot_password");
			Thread.sleep(1000);
			chromeDriver.findElementById("username").sendKeys(account);
			chromeDriver.findElementByCssSelector("#reset-password-submit-button").click();
			Thread.sleep(3000);
			String text = chromeDriver.findElementByCssSelector(".content__header").getText();
			if (text.contains("选择密码修改方式")) {
				return true;
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
