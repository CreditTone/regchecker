package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SouGouSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "搜狗搜索是全球第三代互动式搜索引擎,支持微信公众号和文章搜索、知乎搜索、英文搜索及翻译等,通过自主研发的人工智能算法为用户提供专业、精准、便捷的搜索服务。";
	}

	@Override
	public String platform() {
		return "sogou";
	}

	@Override
	public String home() {
		return "sogou.com";
	}

	@Override
	public String platformName() {
		return "搜狗搜索";
	}

	@Override
	public String[] tags() {
		return new String[] {"工具", "搜索引擎"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new SouGouSpider().checkTelephone("13910000000"));
//		System.out.println(new SouGouSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeImg");
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
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.quicklyVisit("https://www.sogou.com/qq?ru=https%3A%2F%2Fwww.sogou.com%2Flogin%2Fqq_login_callback_page_new.html%3Fxy%3Dhttps%26from%3Dhttps%253A%252F%252Fwww.sogou.com%252F");
			Thread.sleep(3000);
			chromeDriver.mouseClick(chromeDriver.findElementById("sogou_login"));
			chromeDriver.keyboardInput(chromeDriver.findElementByCssSelector("input[class='grey']"), account);
			Thread.sleep(1000);
			String han = chromeDriver.getWindowHandle();
			chromeDriver.findElementByCssSelector(".txt-02").click();
			Thread.sleep(3000);
			chromeDriver.switchTo().window(han);
			WebElement pwd1 = chromeDriver.findElementByCssSelector(".txt-02 #password_input_fake");
			WebElement pwd2 = chromeDriver.findElementByCssSelector(".txt-02 #password_input");
			chromeDriver.jsInput(pwd1, "cas131231");
			chromeDriver.jsInput(pwd2, "cas131231");
			for (int i = 0; i < 5; i++) {
				chromeDriver.reInject();
				chromeDriver.findElementByCssSelector("a.login-btn1").click();
				Thread.sleep(2000);
				String error1 = chromeDriver.findElementByCssSelector(".txt-01 p.error-p").getText();
				String error2 = chromeDriver.findElementByCssSelector(".txt-02 p.error-p").getText();
				if (error1.contains("未注册")) {
					return false;
				}
				if (error2.contains("错误")) {
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

}
