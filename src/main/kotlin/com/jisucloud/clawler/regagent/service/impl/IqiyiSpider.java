package com.jisucloud.clawler.regagent.service.impl;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
public class IqiyiSpider implements PapaSpider {

	private ChromeDriver chromeDriver = null;

	@Override
	public String message() {
		return "iyiqi";
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
		return "大街网";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("视频", new String[] { "社交" });
			}
		};
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(new IqiyiSpider().checkTelephone("13879691485"));
		System.out.println(new IqiyiSpider().checkTelephone("18210538513"));
		System.out.println(new IqiyiSpider().checkTelephone("18210538511"));
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.get("https://www.iqiyi.com/safety/findPassword.html");
			Thread.sleep(1000 * 3);
			WebElement info_input = chromeDriver.findElement(By.cssSelector("input.info-input"));
			info_input.sendKeys(account);
			chromeDriver.findElement(By.cssSelector("a.btn-security")).click();
			Thread.sleep(1000 * 3);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return false;
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
