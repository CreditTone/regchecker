package com.jisucloud.clawler.regagent.service.impl.work;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QiXinBaoSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "启信宝为您提供全国企业信用信息查询服务,包括企业注册信息查询、企业工商信息查询、企业信用查询、企业信息查询等相关信息查询!还可以深入了解企业相关的法人股东,企业。";
	}

	@Override
	public String platform() {
		return "qixin";
	}

	@Override
	public String home() {
		return "qixin.com";
	}

	@Override
	public String platformName() {
		return "启信宝";
	}

	@Override
	public String[] tags() {
		return new String[] {"工具"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new QiXinBaoSpider().checkTelephone("18210538000"));
//		System.out.println(new QiXinBaoSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.setAjaxListener(new AjaxListener() {
				@Override
				public void ajax(Ajax ajax) throws Exception {
					if (ajax.getResponse().contains("密码错误")) {
						checkTel = true;
					}
				}

				@Override
				public String matcherUrl() {
					return "api/user/login";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
			});
			//chromeDriver.get("https://www.qixin.com/");
			chromeDriver.get("https://www.qixin.com/auth/login?return_url=%2F");
			chromeDriver.findElementByCssSelector("input[placeholder=请输入手机号码]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[placeholder=请输入密码]").sendKeys("xkaocgwicb123");
			chromeDriver.findElementByLinkText("登录").click();
			Thread.sleep(3000);
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
