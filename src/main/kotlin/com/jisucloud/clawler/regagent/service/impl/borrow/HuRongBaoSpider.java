package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class HuRongBaoSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	
	@Override
	public String message() {
		return "互融宝-国资控股银行存管双重背景,4年实力品牌,银行存管实现用户资金隔离,凌晨回款T+0资金到账.中国互金+江苏互金双协会会员单位,平台信息披露专区透明公示合规运营,多重严格风控保驾护航-互融宝,100元起加入,投资从互融宝开始。";
	}

	@Override
	public String platform() {
		return "hurbao";
	}

	@Override
	public String home() {
		return "hurbao.com";
	}

	@Override
	public String platformName() {
		return "互融宝";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new HuRongBaoSpider().checkTelephone("15985268904"));
//		System.out.println(new HuRongBaoSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		if (!chromeDriver.checkElement("#captchaImg")) {
			return "";
		}
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#captchaImg");
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
			chromeDriver.get("https://sso.hurbao.com/uc/login");
			Thread.sleep(1000);
			for (int i = 0; i < 5; i++) {
				chromeDriver.findElementById("username").sendKeys(account);
				chromeDriver.findElementById("password").sendKeys("lvnqwnk12mcxn");
				String code = getImgCode();
				if (chromeDriver.checkElement("#piccode")) {
					chromeDriver.findElementById("piccode").sendKeys(code);
				}
				chromeDriver.findElementByCssSelector("a[class='login_btn f18']").click();
				Thread.sleep(3000);
				String error_tip = chromeDriver.findElementById("error_tip").getText();
				if (error_tip.contains("不存在")) {
					return false;
				}
				if (error_tip.contains("不匹配") || error_tip.contains("已锁定")) {
					return true;
				}
				if (error_tip.contains("验证码错误")) {
					continue;
				}
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
