package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "xiangqianjinfu.com", 
		message = "向前金服是捷越联合旗下以智能金融服务为主体的金融科技平台，旨在为用户提供金融信息匹配、信用借款咨询等综合服务。成立以来，向前金服在金融科技领域将大数据、云计算、SAAS、智能引擎等技术渗透于金融管理体系，并建立风险管理模式。", 
		platform = "xiangqianjinfu", 
		platformName = "向前金服", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13910252045", "18210538513" })
public class XiangQianJinFuSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.xiangqianjinfu.com/login");
			chromeDriver.findElementByCssSelector("input[class='el-input__inner']").sendKeys(account);smartSleep(1000);
			chromeDriver.mouseClick(chromeDriver.findElementByCssSelector(".login_submit"));smartSleep(5000);
			if (chromeDriver.checkElement(".login_submit") && chromeDriver.findElementByCssSelector(".login_submit").getText().contains("登录")) {
				return true;
			}
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
