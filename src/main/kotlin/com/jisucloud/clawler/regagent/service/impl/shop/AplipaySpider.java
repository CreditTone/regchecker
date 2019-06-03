package com.jisucloud.clawler.regagent.service.impl.shop;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AplipaySpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;
	
	private boolean checkTelephone = false;

	@Override
	public String message() {
		return "1号店，电子商务型网站，上线于2008年7月 11日，开创了中国电子商务行业 “网上超市”的先河。";
	}

	@Override
	public String platform() {
		return "yhd";
	}

	@Override
	public String home() {
		return "yhd.com";
	}

	@Override
	public String platformName() {
		return "1号店";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("电商", new String[] { });
			}
		};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new AplipaySpider().checkTelephone("13879691485"));
		System.out.println(new AplipaySpider().checkTelephone("18210538513"));
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
			chromeDriver.setAjaxListener(new AjaxListener() {
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					if (ajax.getResponse().contains("密码不匹配") || ajax.getResponse().contains("\\u8d26\\u6237\\u540d\\u4e0e\\u5bc6\\u7801\\u4e0d\\u5339")) {
						checkTelephone = true;
					}
				}

				@Override
				public String matcherUrl() {
					return "/uc/loginService?";
				}
			});
			chromeDriver.get("https://accounts.alipay.com");
			chromeDriver.get("https://accounts.alipay.com/console/querypwd/logonIdInputReset.htm?site=1&page_type=fullpage&scene_code=resetQueryPwd&return_url=");
			chromeDriver.findElementById("J-accName").sendKeys(account);
			chromeDriver.findElementById("nloginpwd").sendKeys(account+"wxl");
			chromeDriver.findElementById("loginsubmit").click();
			Thread.sleep(3000);
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
