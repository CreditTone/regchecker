package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class AmazonSpider implements PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	
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
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13800100001", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://www.amazon.cn/ap/signin?_encoding=UTF8&ignoreAuthState=1&openid.assoc_handle=cnflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.cn%2F%3Fref_%3Dnav_ya_signin&switch_account=");
			Thread.sleep(2000);
			chromeDriver.findElementById("ap_email").sendKeys(account);
			chromeDriver.findElementById("ap_password").sendKeys("xas1223xahh");
			chromeDriver.findElementById("signInSubmit").click();
			Thread.sleep(3000);
			String text = "";
			if (chromeDriver.checkElement("#auth-warning-message-box")) {
				text = chromeDriver.findElementById("auth-warning-message-box").getText();
			}else if (chromeDriver.checkElement("#auth-error-message-box")) {
				text = chromeDriver.findElementById("auth-error-message-box").getText();
			}
			if (text.contains("密码不正确") || text.contains("请重新输入密码")) {
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
