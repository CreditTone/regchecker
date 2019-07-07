package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class RongBeiWangSpider extends PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	@Override
	public String message() {
		return "融贝网是一家优质、透明、普惠的网络借贷信息中介平台,发挥互联网平台高效、便捷的优势,筛选合格出借人与优质借款人,进行撮合服务,构建两者之间的投融资桥梁。";
	}

	@Override
	public String platform() {
		return "irongbei";
	}

	@Override
	public String home() {
		return "irongbei.com";
	}

	@Override
	public String platformName() {
		return "融贝网";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.quicklyVisit("https://www.irongbei.com/userRegister/index");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "https://www.irongbei.com/userRegister/checkMobileAll";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					vcodeSuc = true;
					checkTel = ajax.getResponse().contains("已被注册") || ajax.getResponse().contains("锁定");
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
			chromeDriver.findElementById("mobile").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("captchapic");
				validate.click();smartSleep(3000);
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
