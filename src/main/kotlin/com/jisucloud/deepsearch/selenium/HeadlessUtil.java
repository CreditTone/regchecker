package com.jisucloud.deepsearch.selenium;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeadlessUtil {
	
	public static String CHROME_DRIVER_SERVER = "/root/chromedriver";
	
	static {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			CHROME_DRIVER_SERVER = "/Users/stephen/Downloads/chromedriver";
		}
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_SERVER);
	}
	
	public static FirefoxDriver getFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver","/Users/stephen/Downloads/geckodriver");
		Map<String,Object> timeouts = new HashMap<String,Object>();
		timeouts.put("implicit", 5000);
		timeouts.put("script", 10000);
		timeouts.put("pageLoad", 15000);
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
		desiredCapabilities.setCapability("timeouts", timeouts);
		desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
		Map<String,Object> that = new HashMap<String,Object>();
		desiredCapabilities.setCapability("moz:firefoxOptions", that);
		Map<String, Object> prefs = new HashMap<String,Object>();
		prefs.put("general.useragent.override", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0; uaid:123456) Gecko/20100101123456 Firefox/57.0");
		that.put("prefs", prefs);
		FirefoxDriver firefoxDriver = new FirefoxDriver(desiredCapabilities);
		return firefoxDriver;
	}
	
	public static Set<Cookie> getBaiduCookies() {
		Set<Cookie> result = null;
		ChromeOptions options = new ChromeOptions();
		System.out.println(System.getProperty("os.name").toLowerCase());
		if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			options.setBinary("/usr/bin/google-chrome");
		}
		options.addArguments("--headless");// headless mode
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-gpu");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--disable-setuid-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("window-size=1200x600");
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);
//		prefs.put("profile.managed_default_content_settings.images", 2); // 禁止下载加载图片
		options.setExperimentalOption("prefs", prefs);
		WebDriver mDriver = null;
		try {
			mDriver = new ChromeDriver(options) {
				@Override
				public void get(String url) {
					log.info("begin crawl:"+url);
					try {
						super.get(url);
					}catch(Exception e) {
						log.info("加载url异常:"+url);
					}
					log.info("finish crawl:"+url);
				}
			};
			mDriver.get("https://www.baidu.com");
			Thread.sleep(1000);
			WebElement webElement = mDriver.findElement(By.cssSelector("a[name='tj_trxueshu']"));
			if (webElement != null) {
				webElement.click();
			}
			Thread.sleep(2000);
			mDriver.get("https://www.baidu.com/baidu?wd=bj&tn=monline_4_dg&ie=utf-8&rn=10");
			Thread.sleep(2000);
			webElement = mDriver.findElement(By.cssSelector("a.opr-recommends-merge-mask"));
			if (webElement != null) {
				webElement.click();
			}
			Thread.sleep(2000);
			webElement = mDriver.findElement(By.cssSelector("div[class='result c-container ']"));
			if (webElement != null) {
				webElement.click();
			}
			Thread.sleep(2000);
			mDriver.get("https://tieba.baidu.com/index.html");
			Thread.sleep(2000);
			mDriver.get("https://zhidao.baidu.com/");
			Thread.sleep(2000);
			log.info("cookie get finish");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mDriver != null) {
				result = mDriver.manage().getCookies();
				mDriver.quit();
			}
		}
		return result;
	}
	
	public static Set<Cookie> getGaodeCookies() {
		Set<Cookie> result = null;
		ChromeOptions options = new ChromeOptions();
		if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			options.setBinary("/usr/bin/google-chrome");
		}
		options.addArguments("--headless");// headless mode
		options.addArguments("--disable-gpu");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--disable-setuid-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("window-size=1200x600");
		Map<String, Object> prefs = new HashMap<String, Object>();
//		prefs.put("profile.managed_default_content_settings.images", 2); // 禁止下载加载图片
		options.setExperimentalOption("prefs", prefs);
		WebDriver mDriver = null;
		try {
			mDriver = new ChromeDriver(options) {
				@Override
				public void get(String url) {
					log.info("begin crawl:"+url);
					try {
						super.get(url);
					}catch(Exception e) {
						log.info("加载url异常:"+url);
					}
					log.info("finish crawl:"+url);
				}
			};
			Thread.sleep(2000);
			mDriver.get("https://m.baidu.com/from=1013966u/bd_page_type=1/ssid=0/uid=0/pu=usm%406%2Csz%40320_1001%2Cta%40iphone_2_8.1_19_6.2/baiduid=320270A5D5E182F4C40C7D0EC7862A94/w=0_10_/t=iphone/l=1/tc?clk_type=1&vit=osres&l=1&baiduid=320270A5D5E182F4C40C7D0EC7862A94&w=0_10_%E9%AB%98%E5%BE%B7&t=iphone&ref=www_iphone&from=1013966u&ssid=0&uid=0&lid=11468482634637600467&bd_page_type=1&pu=usm%406%2Csz%40320_1001%2Cta%40iphone_2_8.1_19_6.2&order=1&fm=alop&isAtom=1&waplogo=1&is_baidu=0&tj=www_normal_1_0_10_title&waput=6&cltj=normal_title&asres=1&nt=wnor&title=%E9%AB%98%E5%BE%B7%E5%9C%B0%E5%9B%BE&dict=-1&wd=&eqid=9f283c318e4f6800100000035cda6e4a&w_qd=IlPT2AEptyoA_ykw-vgc6PO&bdver=1&tcplug=1&sec=38527&di=b2c13fd33cfe7dae&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxCGZb8c3JV3T5ABfPNCFZAnSxokDyqRKdJNhZVmqeBSrFS-SlbTPL&clk_info=%7B%22srcid%22%3A1599%2C%22tplname%22%3A%22www_normal%22%2C%22t%22%3A1557818962625%2C%22xpath%22%3A%22div-article%22%7D");
			Thread.sleep(5000);
			log.info("cookie get finish");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mDriver != null) {
				result = mDriver.manage().getCookies();
				mDriver.quit();
			}
		}
		return result;
	}
	
	public static Set<Cookie> getTianyanCookies() {
		Set<Cookie> result = null;
		ChromeOptions options = new ChromeOptions();
		if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			options.setBinary("/usr/bin/google-chrome");
		}
		options.addArguments("--headless");// headless mode
		options.addArguments("--disable-gpu");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--disable-setuid-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("window-size=1200x600");
		Map<String, Object> prefs = new HashMap<String, Object>();
//		prefs.put("profile.managed_default_content_settings.images", 2); // 禁止下载加载图片
		options.setExperimentalOption("prefs", prefs);
		WebDriver mDriver = null;
		try {
			mDriver = new ChromeDriver(options) {
				@Override
				public void get(String url) {
					log.info("begin crawl:"+url);
					try {
						super.get(url);
					}catch(Exception e) {
						log.info("加载url异常:"+url);
					}
					log.info("finish crawl:"+url);
				}
			};
			Thread.sleep(2000);
			mDriver.get("https://m.tianyancha.com");
			Thread.sleep(2000);
			log.info("cookie get finish");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mDriver != null) {
				result = mDriver.manage().getCookies();
				mDriver.quit();
			}
		}
		return result;
	}
	
	
	public static Set<Cookie> getDianpingCookies() {
		Set<Cookie> result = null;
		ChromeOptions options = new ChromeOptions();
		if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			options.setBinary("/usr/bin/google-chrome");
		}
		options.addArguments("--headless");// headless mode
		options.addArguments("--disable-gpu");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--disable-setuid-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("window-size=1200x600");
		Map<String, Object> prefs = new HashMap<String, Object>();
//		prefs.put("profile.managed_default_content_settings.images", 2); // 禁止下载加载图片
		options.setExperimentalOption("prefs", prefs);
		WebDriver mDriver = null;
		try {
			mDriver = new ChromeDriver(options) {
				@Override
				public void get(String url) {
					log.info("begin crawl:"+url);
					try {
						super.get(url);
					}catch(Exception e) {
						log.info("加载url异常:"+url);
					}
					log.info("finish crawl:"+url);
				}
			};
			Thread.sleep(2000);
			mDriver.get("https://m.dianping.com");
			Thread.sleep(2000);
			log.info("cookie get finish");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mDriver != null) {
				result = mDriver.manage().getCookies();
				mDriver.quit();
			}
		}
		return result;
	}
	
	public static Set<Cookie> getEnetCookies() {
		Set<Cookie> result = null;
		ChromeOptions options = new ChromeOptions();
		if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			options.setBinary("/usr/bin/google-chrome");
		}
		options.addArguments("--headless");// headless mode
		options.addArguments("--disable-gpu");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--disable-setuid-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("window-size=1200x600");
		Map<String, Object> prefs = new HashMap<String, Object>();
//		prefs.put("profile.managed_default_content_settings.images", 2); // 禁止下载加载图片
		options.setExperimentalOption("prefs", prefs);
		WebDriver mDriver = null;
		try {
			mDriver = new ChromeDriver(options) {
				@Override
				public void get(String url) {
					log.info("begin crawl:"+url);
					try {
						super.get(url);
					}catch(Exception e) {
						log.info("加载url异常:"+url);
					}
					log.info("finish crawl:"+url);
				}
			};
			Thread.sleep(2000);
			mDriver.get("https://mail.163.com/");
			Thread.sleep(2000);
			mDriver.get("http://reg.email.163.com");
			Thread.sleep(2000);
			log.info("cookie get finish");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mDriver != null) {
				result = mDriver.manage().getCookies();
				mDriver.quit();
			}
		}
		return result;
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
			mDriver.manage().window().maximize();
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
		ChromeDriver chromeDriver = getChromeDriver(false, httpsProxy, null);
		chromeDriver.get("https://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=ip");
		Thread.sleep(30 * 1000);
		chromeDriver.quit();
	}
	
	
}
