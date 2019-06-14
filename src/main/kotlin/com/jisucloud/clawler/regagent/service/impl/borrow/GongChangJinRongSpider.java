package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class GongChangJinRongSpider implements PapaSpider {

	@Override
	public String message() {
		return "工场微金由北京凤凰信用管理有限公司管理运营,专注于向借款人及出借人(用户)提供撮合服务的网络借贷信息中介平台。平台作为首批中国互联网金融协会成员单位及北京网贷。";
	}

	@Override
	public String platform() {
		return "gongchangp2p";
	}

	@Override
	public String home() {
		return "gongchangp2p.com";
	}

	@Override
	public String platformName() {
		return "工场微金";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	private ChromeAjaxListenDriver chromeDriver;
	
	private boolean checkTelephone = false;
	
	//暂时不能访问此页面，被反扒
	public boolean success = false;//默认false
	
	
//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new AiTouZiSpider().checkTelephone("13879690000"));
//		System.out.println(new AiTouZiSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#kaptchaImage");
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
			chromeDriver.get("https://www.gongchangp2p.com/depository/retrieve/toRetrievePwd.shtml?userType=0");
			Thread.sleep(2000);
			chromeDriver.findElementByCssSelector("input[id='phone']").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				String imageCode = getImgCode();
				if (!imageCode.isEmpty()) {
					WebElement codeInput = chromeDriver.findElementByCssSelector("input[id='x_yanzhengma']");
					codeInput.clear();
					codeInput.sendKeys(imageCode);
				}
				chromeDriver.reInject();
				WebElement next = chromeDriver.findElementByCssSelector("input[id='next']");
				next.click();
				Thread.sleep(5000);
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
