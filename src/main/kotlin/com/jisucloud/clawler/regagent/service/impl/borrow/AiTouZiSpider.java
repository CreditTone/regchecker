package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.mockito.internal.util.collections.Sets;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class AiTouZiSpider implements PapaSpider {

	@Override
	public String message() {
		return "最安全规范、实力更强的互联网金融投资理财平台,知名实力P2P网贷品牌,丰富多样且本息全额保障的高收益理财产品,大众化的低投资门槛,全免费的服务体验,爱投资,值得爱。";
	}

	@Override
	public String platform() {
		return "itouzi";
	}

	@Override
	public String home() {
		return "itouzi.com";
	}

	@Override
	public String platformName() {
		return "爱投资";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	private ChromeAjaxListenDriver chromeDriver;
	
	private boolean checkTelephone = false;
	
	//暂时不能访问此页面，被反扒
	public boolean success = false;//默认false
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("13879580000", "18210538513");
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#pwd_login img[class='img-captch']");
				if (!img.isDisplayed()) {
					return "";
				}
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
			chromeDriver.setAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					// TODO Auto-generated method stub
					return "user/ajax/login";
				}
				
				@Override
				public String[] blockUrl() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					if (ajax.getResponse().contains("code\":5") || ajax.getResponse().contains("账号或密码错误") || ajax.getResponse().contains("锁定")) {
						success = true;
						checkTelephone = ajax.getResponse().contains("次机会") || ajax.getResponse().contains("锁定");
					}
					
				}

				@Override
				public String fixPostData() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String fixGetData() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			chromeDriver.get("https://www.itouzi.com/login");
			Thread.sleep(2000);
			chromeDriver.findElementByCssSelector("#pwd_login input[name='username']").sendKeys(account);
			chromeDriver.findElementByCssSelector("#pwd_login input[name='password']").sendKeys("xasp12nxoanx89");
			for (int i = 0; i < 5; i++) {
				String imageCode = getImgCode();
				if (!imageCode.isEmpty()) {
					WebElement codeInput = chromeDriver.findElementByCssSelector("input[name='valicode']");
					codeInput.clear();
					codeInput.sendKeys(imageCode);
				}
				chromeDriver.reInject();
				WebElement next = chromeDriver.findElementByCssSelector("#pwd_login button[class='btn btn-block btn-auto btn-hue2 login']");
				next.click();
				Thread.sleep(15000);
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
