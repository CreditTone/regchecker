package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class VanclSpider extends PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "VANCL（凡客诚品），由卓越网创始人陈年创办于2007年，产品涵盖男装、女装、童装、鞋、家居、配饰、化妆品等七大类，支持全国1100城市货到付款、当面试穿、30天... ";
	}

	@Override
	public String platform() {
		return "vancl";
	}

	@Override
	public String home() {
		return "vancl.com";
	}

	@Override
	public String platformName() {
		return "凡客诚品";
	}

	@Override
	public String[] tags() {
		return new String[] {"电商"};
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
					// TODO Auto-generated method stub
					return "/login/XmlCheckUserName.ashx";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("密码不匹配");
				}
				
				@Override
				public String[] blockUrl() {
					return null;
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
			chromeDriver.get("http://login.vancl.com/login/Login.aspx?http://www.vancl.com?http%3A%2F%2Fwww.vancl.com%2F");smartSleep(3000);
			chromeDriver.findElementById("vanclUserName").sendKeys(account);
			chromeDriver.findElementById("vanclPassword").sendKeys("dsadf312231xs");
			chromeDriver.findElementById("vanclLogin").click();smartSleep(3000);
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
