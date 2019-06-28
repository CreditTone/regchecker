package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.mockito.internal.util.collections.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class ShiTouSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "石投金融是实投(上海)互联网金融信息服务有限公司旗下的互联网金融理财平台，是国内领先的互联网金融网络借贷平台。";
	}

	@Override
	public String platform() {
		return "shitou";
	}

	@Override
	public String home() {
		return "shitou.com";
	}

	@Override
	public String platformName() {
		return "石投金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("13910252045", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			String url = "https://www.shitou.com/login";
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "user/loginNameVerification.do";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("code\":\"000000");
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
			chromeDriver.get("http://www.baidu.com/link?url=MUS_n9bK5KgnJ1M6Y5VG2gT87k7wXd4ejxSLqLyPy-juNLIbe9TAgzlA5__aqaN5&wd=&eqid=cea493ff0001fbdc000000025cf8c6bf");
			chromeDriver.get(url);
			Thread.sleep(3000);
			chromeDriver.findElementByCssSelector("input[ng-model='user.loginName']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[ng-model='user.password']").click();
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
