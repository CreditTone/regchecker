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
public class AiQianJinSpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;
	
	private boolean checkTelephone = false;
	
	//暂时不能访问此页面，被反扒
	public boolean success = false;//默认false

	@Override
	public String message() {
		return "爱钱进是凡普金科旗下网络借贷信息中介平台,位列第三方权威评级机构网贷天眼全国百强榜前十,始终致力于为用户提供简单、公平的互联网金融信息服务,是消费者心中靠谱的网络借贷。";
	}

	@Override
	public String platform() {
		return "iqianjin";
	}

	@Override
	public String home() {
		return "iqianjin.com";
	}

	@Override
	public String platformName() {
		return "爱钱进";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	
//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new AiQianJinSpider().checkTelephone("13879690000"));
//		System.out.println(new AiQianJinSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("img[class='img-code']");
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
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					// TODO Auto-generated method stub
					return "newMPwdBack/sendMobileCode";
				}
				
				@Override
				public String[] blockUrl() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					if (!ajax.getResponse().contains("图片验证码")) {
						success = true;
						checkTelephone = ajax.getResponse().contains("验证码发送成功");
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
			chromeDriver.quicklyVisit("https://m.iqianjin.com");
			chromeDriver.get("https://m.iqianjin.com/m/forgot");
			Thread.sleep(2000);
			chromeDriver.findElementByCssSelector("input[type='tel']").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				String imageCode = getImgCode();
				WebElement codeInput = chromeDriver.findElementByCssSelector("input[placeholder='按右图输入验证码']");
				codeInput.clear();
				codeInput.sendKeys(imageCode);
				WebElement next = chromeDriver.findElementByCssSelector("button.btn-send-code");
				next.click();
				Thread.sleep(3000);
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
