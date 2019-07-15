package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@Slf4j
//@UsePapaSpider
public class GoogleSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "谷歌是一家位于美国的跨国科技企业，业务包括互联网搜索、云计算、广告技术等，同时开发并提供大量基于互联网的产品与服务，其主要利润来自于AdWords等广告服务，被公认为全球最大的搜索引擎公司。";
	}

	@Override
	public String platform() {
		return "google";
	}

	@Override
	public String home() {
		return "google.com";
	}

	@Override
	public String platformName() {
		return "谷歌";
	}

	@Override
	public String[] tags() {
		return new String[] {"软件服务" , "系统工具"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910002005", "18210538513");
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imgObj");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	String code;

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithGoogleProxy(true, false, CHROME_USER_AGENT);
			chromeDriver.get("https://accounts.google.com/signin/v2/identifier?biz=false&hl=zh-CN&continue=https%3A%2F%2Fwww.google.com.hk%2F&gmb=exp&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
			smartSleep(1000);
			chromeDriver.findElementById("identifierId").sendKeys("+86"+account);
			chromeDriver.findElementByCssSelector("span[class='RveJvd snByac']").click();
			smartSleep(3000);
			if (chromeDriver.checkElement("#MemberNameError")) {
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
