package com.jisucloud.deepsearch.selenium.mitm;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import com.jisucloud.clawler.regagent.util.StringUtil;
import com.jisucloud.deepsearch.selenium.ChromeExtensionUtil;
import com.jisucloud.deepsearch.selenium.HttpsProxy;

public class ChromeOptionsUtil {
	
	public static final String USER_AGENTID = "cloudId";
	
	public static final String ANDROID_USER_AGENT = "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36";
	
	public static final String IOS_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A402 Safari/604.1";
	
	public static final String CHROME_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0";
	
	public static String CHROME_DRIVER_SERVER = "/root/chromedriver";
	
	public static HttpsProxy httpsProxy = new HttpsProxy("http-dyn.abuyun.com", 9020, "H6224X2YF291C2AD", "2EADA65DEE87F60C");
	
	static {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			CHROME_DRIVER_SERVER = "/Users/stephen/Downloads/chromedriver";
		}
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            CHROME_DRIVER_SERVER = CHROME_DRIVER_SERVER + ".exe";
            File file = new File(CHROME_DRIVER_SERVER);
            if (!file.exists()) {
                throw new NullPointerException("去下载个ChromeDriver.exe放到:" + file.getAbsolutePath());
            }
        }
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_SERVER);
	}
	
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
		if (userAgent == null) {
			userAgent = CHROME_USER_AGENT;
		}
		String cloudIdValue = StringUtil.getMD5(UUID.randomUUID().toString());
		userAgent += " Cloud/" + cloudIdValue;
		options.addArguments("--user-agent='"+userAgent+"'");
		options.setCapability(USER_AGENTID, cloudIdValue);
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
