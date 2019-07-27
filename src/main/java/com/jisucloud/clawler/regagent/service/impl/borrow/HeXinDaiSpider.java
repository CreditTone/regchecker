package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class HeXinDaiSpider extends PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "和信贷，纳斯达克上市互联网金融平台。注册资本1亿元，5年稳健运营，AAA级互联网信用认证，央行支付清算协会互联网金融风险信息共享系统接入单位，严谨的风险管控保护体系，为您提供专业、透明的出借服务！";
	}

	@Override
	public String platform() {
		return "hexindai";
	}

	@Override
	public String home() {
		return "hexindai.com";
	}

	@Override
	public String platformName() {
		return "和信贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15201215815", "18210538513");
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			String url = "https://www.hexindai.com/register";
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "check/username/exist";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("existed\":true");
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
			chromeDriver.get(url);smartSleep(3000);
			chromeDriver.findElementByCssSelector("input[id=registerPhone]").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[id=registerPwd]").click();smartSleep(3000);
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
