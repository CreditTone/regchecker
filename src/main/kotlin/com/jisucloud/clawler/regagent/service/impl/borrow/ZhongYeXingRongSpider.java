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

import java.util.Map;
import java.util.Random;
import java.util.Set;

@Slf4j
//@UsePapaSpider
public class ZhongYeXingRongSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "中业兴融(zyxr.com)是专业可信赖的P2P网贷平台,拥有超过100万注册用户,为用户提供专业透明的投资渠道和优质先进的科技金融服务,用户可通过平台进行定期投资获得稳定。";
	}

	@Override
	public String platform() {
		return "zyxr";
	}

	@Override
	public String home() {
		return "zyxr.com";
	}

	@Override
	public String platformName() {
		return "中业兴融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("15201215815", "18210538513");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector(".form-control img");
				img.click();
				Thread.sleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "ne5");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null , ANDROID_USER_AGENT);
			chromeDriver.get("https://www.zyxr.com/");
			chromeDriver.get("https://www.zyxr.com/wap/?chl=bd-keyword-wap#/login?backUrl=https%3A%2F%2Fwww.zyxr.com%2Fwap%2F%3Fchl%3Dbd-keyword-wap%23%2Flogin");
			Thread.sleep(3000);
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "https://www.zyxr.com/UserWeb/login.json";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					if (!ajax.getResponse().contains("验证码错误")) {
						vcodeSuc = true;
						checkTel = ajax.getResponse().contains("密码错误") || ajax.getResponse().contains("锁定");
					}
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
			chromeDriver.findElementByCssSelector("input[type='tel']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[type='password']").sendKeys("x19cn10xn" + new Random().nextInt(100000));
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementByCssSelector("input[placeholder='请输入图形验证码']");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.reInject();
				chromeDriver.findElementById("button[class='btn btn-primary btn-block']").click();
				Thread.sleep(3000);
				if (vcodeSuc) {
					break;
				}
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
