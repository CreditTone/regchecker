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
public class XiaoNiuZaiXianSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "91旺财是九一金融旗下互联网网络借贷信息中介平台,北京市互联网金融行业协会副会长单位,中国互联网金融协会会员理事单位,公司法人许泽玮先生现任北京市互联网金融协会。";
	}

	@Override
	public String platform() {
		return "91wangcai";
	}

	@Override
	public String home() {
		return "91wangcai.com";
	}

	@Override
	public String platformName() {
		return "91旺财";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new XiaoNiuZaiXianSpider().checkTelephone("13910252045"));
//		System.out.println(new XiaoNiuZaiXianSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#step1-img-code");
				img.click();
				Thread.sleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "ne6");
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
			chromeDriver.quicklyVisit("https://www.xiaoniu88.com/user/forgetpassword");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "https://www.xiaoniu88.com/user/forgetpassword/verifycode";
				}
				
				@Override
				public String[] blockUrl() {
					return new String[] {"user/forgetpassword/step1"};
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					vcodeSuc =  ajax.getResponse().contains("0") || ajax.getResponse().contains("9");
					checkTel = ajax.getResponse().contains("0");
				}

				@Override
				public String fixPostData() {
					return null;
				}

				@Override
				public String fixGetData() {
					return null;
				}
			});
			chromeDriver.findElementById("username").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("randomCode");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.reInject();
				chromeDriver.findElementById("step1-btn").click();
				Thread.sleep(3000);
				if (vcodeSuc) {
					break;
				}
				if (chromeDriver.getCurrentUrl().contains("user/forgetpassword/step1")) {
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
