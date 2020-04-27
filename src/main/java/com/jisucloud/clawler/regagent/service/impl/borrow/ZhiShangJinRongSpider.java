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
		home = "i2p.com", 
		message = "知商金融(www.i2p.com) -领先互联网知识产权金融平台。排名靠前的p2p投资公司,通过知识产权做抵押,为您提供各种收益高的投资产品,会投资,更赚钱!", 
		platform = "i2p", 
		platformName = "知商金融", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13912345678", "18212345678" })
public class ZhiShangJinRongSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeimg");
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
			chromeDriver.get("https://www.i2p.com/signin/users_getpwd.action?type=phone&step=one");
			chromeDriver.keyboardInput(chromeDriver.findElementById("phone"), account);
			for (int i = 0; i < 3; i++) {
				WebElement validate = chromeDriver.findElementById("valicode");
				validate.clear();
				chromeDriver.keyboardInput(validate, getImgCode());
				chromeDriver.findElementById("get_pwd_submit").click();smartSleep(3000);
				if (chromeDriver.checkElement("#get_verty_btn")) {
					return true;
				}
				if (chromeDriver.checkElement("#valicodeDiv .loginInputTips")) {
					String text = chromeDriver.findElementByCssSelector("#valicodeDiv .loginInputTips").getText();
					if (text.contains("不存在")) {
						return false;
					}
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
