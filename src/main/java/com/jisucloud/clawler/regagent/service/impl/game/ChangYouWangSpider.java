package com.jisucloud.clawler.regagent.service.impl.game;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class ChangYouWangSpider extends PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "畅游有限公司(纳斯达克股票交易代码:CYOU),中国在线游戏开发和运营商之一,自主研发的《新天龙八部》是中国最受欢迎的大型多人在线角色扮演游戏之一。";
	}

	@Override
	public String platform() {
		return "changyou";
	}

	@Override
	public String home() {
		return "changyou.com";
	}

	@Override
	public String platformName() {
		return "畅游网";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15700102865", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			String url = "http://zhuce.changyou.com/reg.act?gameType=PE-ZHPT&invitecode=&regWay=phone&suffix=";
			chromeDriver.setAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "baseReg/checkCnIsUsed.act";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("used");
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
			chromeDriver.findElementById("securityPhone").sendKeys(account);
			chromeDriver.findElementById("passwd_phone").click();smartSleep(3000);
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
