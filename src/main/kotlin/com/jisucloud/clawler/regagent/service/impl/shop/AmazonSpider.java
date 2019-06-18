package com.jisucloud.clawler.regagent.service.impl.shop;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AmazonSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	
	@Override
	public String message() {
		return "亚马逊中国(z.cn)坚持“以客户为中心”的理念,秉承“天天低价,正品行货”信念,销售图书、电脑、数码家电、母婴百货、服饰箱包等上千万种产品。";
	}

	@Override
	public String platform() {
		return "amazon";
	}

	@Override
	public String home() {
		return "amazon.com";
	}

	@Override
	public String platformName() {
		return "亚马逊";
	}

	@Override
	public String[] tags() {
		return new String[] {"电商" , "海购"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new AmazonSpider().checkTelephone("18210538000"));
//		System.out.println(new AmazonSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			chromeDriver.get("https://www.amazon.cn/ap/signin?_encoding=UTF8&ignoreAuthState=1&openid.assoc_handle=cnflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.cn%2F%3Fref_%3Dnav_ya_signin&switch_account=");
			Thread.sleep(3000);
			chromeDriver.findElementById("auth-fpp-link-bottom").click();
			Thread.sleep(5000);
			chromeDriver.findElementById("ap_email").sendKeys(account);
			chromeDriver.findElementById("continue").click();
			Thread.sleep(3000);
			String res = chromeDriver.getPageSource();
			if (res.contains("使用临时代码登录")) {
				return true;
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
