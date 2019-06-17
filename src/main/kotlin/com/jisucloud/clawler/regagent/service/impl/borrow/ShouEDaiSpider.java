package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ShouEDaiSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;

	@Override
	public String message() {
		return "首e贷是首创金服属于首创集团下属公司,国企控股金融科技公司,引导社会资金支持实体经济,降低中小微企业融资成本,中国互联网金融协会创始会员之一。";
	}

	@Override
	public String platform() {
		return "syr66";
	}

	@Override
	public String home() {
		return "syr66.com";
	}

	@Override
	public String platformName() {
		return "首e贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new ShouEDaiSpider().checkTelephone("13910252045"));
		System.out.println(new ShouEDaiSpider().checkTelephone("18210538513"));
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imc_verfication_code");
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
			chromeDriver.get("https://www.syr66.com/findback/findBackLoginPassword.htm");
			chromeDriver.findElementById("PhoneNumber").sendKeys(account);
			chromeDriver.findElementById("PersonCardId").sendKeys("320219196503306777");
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("vericode");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.reInject();
				chromeDriver.findElementByCssSelector(".verA2017_btnpcred").click();;
				Thread.sleep(8000);
				if (chromeDriver.checkElement("#huoqupw")) {
					return true;
				}
				if (chromeDriver.getCurrentUrl().contains("findBackLoginPassword")) {
					String vericodeMessage = chromeDriver.findElementById("vericodeMessage").getText();
					if (vericodeMessage.contains("错误")) {
						continue;
					}
					String backErrorMessage = chromeDriver.findElementById("backErrorMessage").getText();
					if (backErrorMessage.length() > 3 && !backErrorMessage.contains("未注册")) {
						return true;
					}
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
		return false;
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
