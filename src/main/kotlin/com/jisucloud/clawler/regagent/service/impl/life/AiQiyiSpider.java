package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AiQiyiSpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "爱奇艺是由龚宇于2010年4月22日创立的视频网站，2011年11月26日启动“爱奇艺”品牌并推出全新标志。爱奇艺成立伊始，坚持“悦享品质”的公司理念，以“用户体验..";
	}

	@Override
	public String platform() {
		return "iqiyi";
	}

	@Override
	public String home() {
		return "iqiyi.com";
	}

	@Override
	public String platformName() {
		return "爱奇艺";
	}

	@Override
	public String[] tags() {
		return new String[] {"影音", "视频", "MV"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new AiQiyiSpider().checkTelephone("18210538513"));
//		System.out.println(new AiQiyiSpider().checkTelephone("18210530000"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.setAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "user/check_account.action";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("data\":true");
				}

				@Override
				public String[] blockUrl() {
					return null;
				}
			});
			chromeDriver.get("http://www.iqiyi.com/iframe/loginreg?ver=1");
			Thread.sleep(2000);
			chromeDriver.findElementByLinkText("账号密码登录").click();
			Thread.sleep(1000);
			chromeDriver.findElementByLinkText("忘记密码").click();
			Thread.sleep(1000);
			chromeDriver.findElementByLinkText("短信登录").click();
			Thread.sleep(1000);
			WebElement nameInputArea = chromeDriver.findElementByCssSelector("input[data-regbox='name']");
			nameInputArea.sendKeys(account);
			chromeDriver.findElementByLinkText("下一步").click();
			Thread.sleep(2000);
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
