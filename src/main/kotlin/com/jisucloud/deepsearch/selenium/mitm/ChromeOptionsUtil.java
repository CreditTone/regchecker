package com.jisucloud.deepsearch.selenium.mitm;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.jisucloud.deepsearch.selenium.ChromeExtensionUtil;
import com.jisucloud.deepsearch.selenium.HttpsProxy;

public class ChromeOptionsUtil {

	public static ChromeOptions createChromeOptions(boolean disableLoadImage,boolean headless,HttpsProxy proxy,String userAgent) {
		ChromeOptions options = new ChromeOptions();
		if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			options.setBinary("/usr/bin/google-chrome");
		}
		if (headless) {
			options.addArguments("--headless");// headless mode
		}
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
		return options;
	}
}
