package com.jisucloud.deepsearch.selenium;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.deep077.spiderbase.selenium.mitm.ChromeOptionsUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeadlessUtil {
	
	static {
		System.setProperty("webdriver.chrome.driver", ChromeOptionsUtil.CHROME_DRIVER_SERVER);
	}
	
	public static ChromeAjaxListenDriver getChromeDriver(boolean disableLoadImage,HttpsProxy proxy,String userAgent) throws Exception {
		ChromeOptions options = new ChromeOptions();
		if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			options.setBinary("/usr/bin/google-chrome");
		}
		options.addArguments("--headless");// headless mode
		options.addArguments("--disable-gpu");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--disable-dev-shm-usage");
		if (userAgent != null) {
			options.addArguments("--user-agent='"+userAgent+"'");
		}
		if (proxy != null) {
			if (proxy.getUsername() != null && proxy.getPassword() != null) {
				File extension = ChromeExtensionUtil.createProxyauthExtension(proxy.getServer(), proxy.getPort(), proxy.getUsername(), proxy.getPassword());
				options.addExtensions(extension);
			}else {
				options.addArguments("proxy-server="+proxy.getServer()+":"+proxy.getPort());
			}
		}
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		prefs.put("profile.default_content_settings.popups", 0);
		if (disableLoadImage) {
			prefs.put("profile.managed_default_content_settings.images",2); //禁止下载加载图片
		}
		options.setExperimentalOption("prefs", prefs);
		ChromeAjaxListenDriver mDriver = null;
		try {
			mDriver = new ChromeAjaxListenDriver(options);
			mDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);//脚步执行超时
			mDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);//页面加载超时
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mDriver;
	}
	
	public static void main(String[] args) throws Exception {
		HttpsProxy httpsProxy = new HttpsProxy("http-dyn.abuyun.com", 9020, "H6224X2YF291C2AD", "2EADA65DEE87F60C");
		ChromeDriver chromeDriver = getChromeDriver(false, null, null);
		chromeDriver.get("https://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=ip");Thread.sleep(30 * 1000);
		chromeDriver.quit();
	}
	
	
}
