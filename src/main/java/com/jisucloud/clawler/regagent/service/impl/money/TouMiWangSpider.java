package com.jisucloud.clawler.regagent.service.impl.money;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class TouMiWangSpider extends PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	@Override
	public String message() {
		return "投米网是宜信财富旗下智能理财平台,致力于为投资者提供安全、透明、便捷、低门槛、高收益的理财服务,并且在该理财平台上100元即可加入理财计划。";
	}

	@Override
	public String platform() {
		return "itoumi";
	}

	@Override
	public String home() {
		return "itoumi.com";
	}

	@Override
	public String platformName() {
		return "投米网";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "理财"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#captchaimg");
				img.click();smartSleep(1000);
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
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, CHROME_USER_AGENT);
			chromeDriver.quicklyVisit("https://www.baidu.com/link?url=ngnLhflEeJi3KzA5RI0iCSDZFS_4SyR0IrVBkp_Uq56r9X7dc7airUuEGcTpVWIG&wd=&eqid=b3d32cfd0007c899000000025d088c88");
			chromeDriver.get("https://www.itoumi.com/register.shtml");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "https://www.itoumi.com/registerCheckUniqueMobile.jspx";
				}
				
				@Override
				public String[] blockUrl() {
					return new String[] {
							"openapi/sendValidateCode.jspx"
					};
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					vcodeSuc = true;
					checkTel = ajax.getResponse().contains("已注册");
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
			});smartSleep(3000);
			chromeDriver.findElementById("phone").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("lvnqwnk12mcxn");
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("verifyCode");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.reInject();
				chromeDriver.findElementById("register_page_submit").click();smartSleep(3000);
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
