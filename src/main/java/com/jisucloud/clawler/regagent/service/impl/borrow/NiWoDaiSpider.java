package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "niwodai.com", 
		message = "你我贷(niwodai.com)是美股上市公司嘉银金科旗下网络借贷信息中介平台，实缴注册资本5.5亿元。专业提供便捷、低门槛的个人借款和多元化的出借策略。", 
		platform = "niwodai", 
		platformName = "你我贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15900068904", "18210538513" })
public class NiWoDaiSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;
	
	private boolean checkTelephone = false;
	
	//暂时不能访问此页面，被反扒
	public boolean success = false;//默认false

	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("img#code");
				img.click();smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(false, true);
//			chromeDriver.quicklyVisit("https://m.niwodai.com/home");
			chromeDriver.get("https://m.niwodai.com/register/v2/wap/login/index");smartSleep(2000);
			chromeDriver.findElementById("username").sendKeys(account);
			chromeDriver.findElementById("pwd").sendKeys("xkabdsd12ao");
			for (int i = 0; i < 5; i++) {
				chromeDriver.findElementById("pwd").clear();
				chromeDriver.findElementById("pwd").sendKeys("xkabdsd12ao");
				if (chromeDriver.checkElement("#imgCode")) {
					String imageCode = getImgCode();
					WebElement imgCode = chromeDriver.findElementById("imgCode");
					imgCode.clear();
					imgCode.sendKeys(imageCode);
				}smartSleep(500);
				WebElement next = chromeDriver.findElementById("userLoginBtn");
				next.click();smartSleep(1000);
				String popText = chromeDriver.findElementByCssSelector("div[class='pop_s pop_show']").getText();
				System.out.println(popText);
				success = popText.contains("密码错误");
				if (success) {
					checkTelephone = popText.contains("密码错误，");
				}
				if (success) {
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
		return checkTelephone;
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
