package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.mockito.internal.util.collections.Sets;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class XiangQianJinFuSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "向前金服是捷越联合旗下以智能金融服务为主体的金融科技平台，旨在为用户提供金融信息匹配、信用借款咨询等综合服务。成立以来，向前金服在金融科技领域将大数据、云计算、SAAS、智能引擎等技术渗透于金融管理体系，并建立风险管理模式。";
	}

	@Override
	public String platform() {
		return "xiangqianjinfu";
	}

	@Override
	public String home() {
		return "xiangqianjinfu.com";
	}

	@Override
	public String platformName() {
		return "向前金服";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("13910252045", "18210538513");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeImg");
				img.click();
				Thread.sleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.get("https://www.xiangqianjinfu.com/login");
			chromeDriver.findElementByCssSelector("input[class='el-input__inner']").sendKeys(account);
			Thread.sleep(1000);
			chromeDriver.mouseClick(chromeDriver.findElementByCssSelector(".login_submit"));
			Thread.sleep(5000);
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
