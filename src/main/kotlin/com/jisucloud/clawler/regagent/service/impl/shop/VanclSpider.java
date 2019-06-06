package com.jisucloud.clawler.regagent.service.impl.shop;

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
public class VanclSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "VANCL（凡客诚品），由卓越网创始人陈年创办于2007年，产品涵盖男装、女装、童装、鞋、家居、配饰、化妆品等七大类，支持全国1100城市货到付款、当面试穿、30天... ";
	}

	@Override
	public String platform() {
		return "vancl";
	}

	@Override
	public String home() {
		return "vancl.com";
	}

	@Override
	public String platformName() {
		return "凡客诚品";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("金融", new String[] { "储蓄"});
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new VanclSpider().checkTelephone("18210538000"));
//		System.out.println(new VanclSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			chromeDriver.setAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					// TODO Auto-generated method stub
					return "/login/XmlCheckUserName.ashx";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("密码不匹配");
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
			});
			chromeDriver.get("http://login.vancl.com/login/Login.aspx?http://www.vancl.com?http%3A%2F%2Fwww.vancl.com%2F");
			Thread.sleep(3000);
			chromeDriver.findElementById("vanclUserName").sendKeys(account);
			chromeDriver.findElementById("vanclPassword").sendKeys("dsadf312231xs");
			chromeDriver.findElementById("vanclLogin").click();
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
