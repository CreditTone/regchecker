package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class BiYouXinSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "碧有信金融（www.biyouxin.com）成立于2016年4月，注册资金5000万元，是国内著名房产企业——碧桂园集团旗下互联网金融平台。";
	}

	@Override
	public String platform() {
		return "biyouxin";
	}

	@Override
	public String home() {
		return "biyouxin.com";
	}

	@Override
	public String platformName() {
		return "碧有信金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷" , "商业房贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new BiYouXinSpider().checkTelephone("13910252045"));
//		System.out.println(new BiYouXinSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeImg");
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
			chromeDriver.get("https://cms.biyouxinjr.com/userCenter/#/register");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "api/checkUserExist";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					vcodeSuc = true;
					JSONObject result = JSON.parseObject(ajax.getResponse());
					checkTel = result.getJSONObject("res_data").get("isExist").toString().equals("1");
				}
			});
			chromeDriver.findElementByCssSelector("input[type='tel']").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				chromeDriver.reInject();
				chromeDriver.findElementByCssSelector("input[type='password']").click();
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
