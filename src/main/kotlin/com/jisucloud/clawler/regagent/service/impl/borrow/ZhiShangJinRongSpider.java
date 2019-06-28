package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.internal.util.collections.Sets;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class ZhiShangJinRongSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "知商金融(www.i2p.com) -领先互联网知识产权金融平台。排名靠前的p2p投资公司,通过知识产权做抵押,为您提供各种收益高的投资产品,会投资,更赚钱!";
	}

	@Override
	public String platform() {
		return "i2p";
	}

	@Override
	public String home() {
		return "i2p.com";
	}

	@Override
	public String platformName() {
		return "知商金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("13910252045", "18210538513");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeimg");
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
			chromeDriver.get("https://www.i2p.com/signin/users_getpwd.action?type=phone&step=one");
			chromeDriver.keyboardInput(chromeDriver.findElementById("phone"), account);
			for (int i = 0; i < 3; i++) {
				WebElement validate = chromeDriver.findElementById("valicode");
				validate.clear();
				chromeDriver.keyboardInput(validate, getImgCode());
				chromeDriver.findElementById("get_pwd_submit").click();
				Thread.sleep(3000);
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
