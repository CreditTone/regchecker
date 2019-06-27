package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@UsePapaSpider
public class HuaShengMiFuSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "花生米富官方网站,作为新一代互联网金融平台,在健全的风险管控体系、银行存管、三级等保基础上,提供多重风控、真实债权、消费场景等信息服务。为您的出借保驾护航!";
	}

	@Override
	public String platform() {
		return "yaoyuefu";
	}

	@Override
	public String home() {
		return "yaoyuefu.com";
	}

	@Override
	public String platformName() {
		return "花生米富";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new HuaShengMiFuSpider().checkTelephone("13910252000"));
//		System.out.println(new HuaShengMiFuSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			String url = "https://www.yaoyuefu.com/default/reg";
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "default/is_reg";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("已注册");
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
			chromeDriver.get(url);
			Thread.sleep(3000);
			chromeDriver.findElementByCssSelector("#mobile").sendKeys(account);
			chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("#code"));
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
