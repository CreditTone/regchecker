package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Slf4j
@UsePapaSpider
public class SuNingSpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "苏宁易购，是苏宁易购集团股份有限公司旗下新一代B2C网上购物平台，现已覆盖传统家电、3C电器、日用百货等品类。2011年，苏宁易购强化虚拟网络与实体店面的同步发展，不断提升网络市场份额。";
	}

	@Override
	public String platform() {
		return "suning";
	}

	@Override
	public String home() {
		return "suning.com";
	}

	@Override
	public String platformName() {
		return "苏宁易购";
	}

	@Override
	public String[] tags() {
		return new String[] {"购物" , "电器"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210530000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			chromeDriver.setAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "ajaxCheckAliasEPP.do";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("线上已存在");
				}

				@Override
				public String[] blockUrl() {
					return new String[] {"sms.do"};
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
			chromeDriver.get("https://reg.suning.com/person.do?myTargetUrl=https%3A%2F%2Fwww.suning.com%2F");
			Thread.sleep(2000);
			chromeDriver.findElementByLinkText("同意并继续").click();
			Thread.sleep(1000);
			chromeDriver.findElementById("mobileAlias").sendKeys(account);
			chromeDriver.findElementById("sendSmsCode").click();
			Thread.sleep(2000);
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
