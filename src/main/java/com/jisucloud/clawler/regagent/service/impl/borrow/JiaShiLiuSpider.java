package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
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
public class JiaShiLiuSpider extends PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "嘉实金融信息服务(杭州)有限公司(简称“嘉实金服”)设立于浙江省杭州市,注册资本人民币5000万元。嘉实金服作为信息服务平台,运营和维护“嘉石榴”网站。";
	}

	@Override
	public String platform() {
		return "jia16";
	}

	@Override
	public String home() {
		return "jia16.com";
	}

	@Override
	public String platformName() {
		return "嘉石榴";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.quicklyVisit("https://www.jia16.com/register");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "check";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					System.out.println(ajax);
					checkTel = ajax.getResponse().contains("已存在");
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
			chromeDriver.findElementById("phone").sendKeys(account);
			chromeDriver.reInject();
			chromeDriver.findElementByCssSelector("input[name='password']").click();smartSleep(3000);
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
