package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Sets;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class HouBenJinRongSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "厚本金融(houbank.com)是专业的互联网借贷撮合平台,为出借人和借款人提供优质的互联网金融借贷信息中介服务,中华财险保证保险逐步覆盖平台资产。";
	}

	@Override
	public String platform() {
		return "houbank";
	}

	@Override
	public String home() {
		return "houbank.com";
	}

	@Override
	public String platformName() {
		return "厚本金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
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
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, CHROME_USER_AGENT);
			chromeDriver.quicklyVisit("http://www.baidu.com/link?url=dfL9ZPOGYGBiMGQ2RqQdw810qctmkrOMZm_IZoVE9VbbL9E1a1GOvQZhr2Xn0rbM&wd=&eqid=802dd3a500059a35000000025d061f5a");
			chromeDriver.quicklyVisit("https://www.houbank.com/pro/account/forget");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "webserver/user/checkImageCode";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					if (!ajax.getResponse().contains("验证码错误")) {
						vcodeSuc = true;
						checkTel = ajax.getResponse().contains("图片验证码校验成功");
					}
				}

				@Override
				public String fixPostData() {
					return "{\"imgCod\":\"2586\",\"mobile\":\""+account+"\"}";
				}

				@Override
				public String fixGetData() {
					return null;
				}
			});
			Thread.sleep(1000);
			//chromeDriver.findElementByCssSelector("input[placeholder='请输入手机号']").sendKeys(account);
			chromeDriver.jsInput(chromeDriver.findElementByCssSelector("input[placeholder='请输入手机号']"), account);
			chromeDriver.jsInput(chromeDriver.findElementByCssSelector("input[placeholder='请输入图形验证码']"), "3579");
			//chromeDriver.reInject();
			Thread.sleep(3000);
			chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("input[class='step__btn']"));
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
