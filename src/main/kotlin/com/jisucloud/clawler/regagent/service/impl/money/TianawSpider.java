package com.jisucloud.clawler.regagent.service.impl.money;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TianawSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "天安保险即天安财产保险股份有限公司是中国第四家财产险保险公司，也是第二家按照现代企业制度和国际标准组建的股份制商业保险公司。";
	}

	@Override
	public String platform() {
		return "tianaw";
	}

	@Override
	public String home() {
		return "tianaw.com";
	}

	@Override
	public String platformName() {
		return "天安保险";
	}

	@Override
	public String[] tags() {
		return new String[] {"理财", "保险"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new TianawSpider().checkTelephone("18210538000"));
//		System.out.println(new TianawSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			String url = "https://tianaw.95505.cn/tacpc/#/login/updatepass";
			chromeDriver.setAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "/customer_login/setPassword?jsonKey";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = !ajax.getResponse().contains("该用户未注册");
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
			chromeDriver.get(url);
			Thread.sleep(5000);
			chromeDriver.findElementById("'phoneNumber'").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("wxy"+account);
			chromeDriver.findElementById("checkPassword").sendKeys("wxy"+account);
			chromeDriver.findElementByCssSelector("button[nztype=primary]").click();
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
