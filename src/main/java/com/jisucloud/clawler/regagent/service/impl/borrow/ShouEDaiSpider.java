package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;

import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;

import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "syr66.com", 
		message = "首e贷是首创金服属于首创集团下属公司,国企控股金融科技公司,引导社会资金支持实体经济,降低中小微企业融资成本,中国互联网金融协会创始会员之一。", 
		platform = "syr66", 
		platformName = "首e贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13912345678", "18212345678" })
public class ShouEDaiSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imc_verfication_code");
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
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.get("https://www.syr66.com/findback/findBackLoginPassword.htm");
			chromeDriver.findElementById("PhoneNumber").sendKeys(account);
			chromeDriver.findElementById("PersonCardId").sendKeys("320219196503306777");
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("vericode");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.findElementByCssSelector(".verA2017_btnpcred").click();;smartSleep(8000);
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
