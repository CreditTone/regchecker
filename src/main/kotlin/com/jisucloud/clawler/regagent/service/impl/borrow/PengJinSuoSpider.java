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
public class PengJinSuoSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "鹏金所,全称深圳市鹏金所互联网金融服务有限公司,是万科领衔多家上市公司联袂打造的互联网金融平台。鹏金所是深圳市鹏鼎创盈金融信息服务股份有限公司。";
	}

	@Override
	public String platform() {
		return "penging";
	}

	@Override
	public String home() {
		return "penging.com";
	}

	@Override
	public String platformName() {
		return "鹏金所";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new PengJinSuoSpider().checkTelephone("13910252045"));
		System.out.println(new PengJinSuoSpider().checkTelephone("18210538513"));
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imgObj");
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
	String code;

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			chromeDriver.quicklyVisit("http://www.penging.com/findPwd.do");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "http://www.penging.com/login/phoneCheckFindPwd.do";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					if (!vcodeSuc && !ajax.getResponse().contains("验证码错误")) {
						vcodeSuc = true;
						checkTel = ajax.getResponse().contains("密码错误") || ajax.getResponse().contains("锁定");
					}
				}

				@Override
				public String fixPostData() {
					//不能实时通信
					return "MB_PHN="+account+"&c="+code+"&CI_NM=";
				}

				@Override
				public String fixGetData() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			chromeDriver.findElementById("MB_PHN").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				code = getImgCode();
				WebElement validate = chromeDriver.findElementById("CFY_NO");
				validate.clear();
				validate.sendKeys(code);
				chromeDriver.reInject();
				chromeDriver.findElementById("showTxt").click();
				Thread.sleep(3000);
				if (vcodeSuc) {
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
