package com.jisucloud.clawler.regagent.service.impl.social;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;


import java.util.Map;

@Slf4j
@UsePapaSpider
public class WoZaiZhaoNiSpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;


	@Override
	public String message() {
		return "我在找你-相亲交友,完全免费!所有认证会员都经过实名制核实!为亿万单身男女提供完全免费、真实可信、简便高效的相亲交友聊天平台!";
	}

	@Override
	public String platform() {
		return "95195";
	}

	@Override
	public String home() {
		return "95195.com";
	}

	@Override
	public String platformName() {
		return "我在找你";
	}

	@Override
	public String[] tags() {
		return new String[] {"单身交友"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new WoZaiZhaoNiSpider().checkTelephone("18515290717"));
//		System.out.println(new WoZaiZhaoNiSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.quicklyVisit("http://www.95195.com");
			chromeDriver.get("http://www.95195.com/user/reg/");
			chromeDriver.findElementById("r_mobile").sendKeys(account);
			chromeDriver.findElementById("nickname").click();
			Thread.sleep(2000);
			String text = chromeDriver.findElementById("mobile_explain").getText();
			return text.contains("已经注册");
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
