package com.jisucloud.deepsearch.selenium.mitm;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import com.alibaba.fastjson.JSON;
import com.jisucloud.deepsearch.selenium.HttpsProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChromeAjaxHookDriver extends ChromeDriver {
	
	public static final Random random = new Random();
	
	private String cloudIdValue = null;
	
	public static final ChromeAjaxHookDriver newChromeInstance(boolean disableLoadImage,boolean headless) {
		return new ChromeAjaxHookDriver(ChromeOptionsUtil.createChromeOptions(disableLoadImage, headless, MitmServer.getInstance().getLocalMitmProxy(), ChromeOptionsUtil.CHROME_USER_AGENT));
	}
	
	public static final ChromeAjaxHookDriver newAndroidInstance(boolean disableLoadImage,boolean headless) {
		return new ChromeAjaxHookDriver(ChromeOptionsUtil.createChromeOptions(disableLoadImage, headless, MitmServer.getInstance().getLocalMitmProxy(), ChromeOptionsUtil.ANDROID_USER_AGENT));
	}
	
	public static final ChromeAjaxHookDriver newIOSInstance(boolean disableLoadImage,boolean headless) {
		return new ChromeAjaxHookDriver(ChromeOptionsUtil.createChromeOptions(disableLoadImage, headless, MitmServer.getInstance().getLocalMitmProxy(), ChromeOptionsUtil.IOS_USER_AGENT));
	}
	
	public static final ChromeAjaxHookDriver newNoHookInstance(boolean disableLoadImage,boolean headless,String userAgent) {
		log.warn("你启动的是没有钩子功能的浏览器");
		return new ChromeAjaxHookDriver(ChromeOptionsUtil.createChromeOptions(disableLoadImage, headless, null, userAgent));
	}
	
	public static final ChromeAjaxHookDriver newInstanceWithRandomProxy(boolean disableLoadImage,boolean headless,String userAgent) {
		return new ChromeAjaxHookDriver(ChromeOptionsUtil.createChromeOptions(disableLoadImage, headless, MitmServer.getInstance().getRandomMitmProxy(), userAgent));
	}
	
	public static final ChromeAjaxHookDriver newInstanceWithGoogleProxy(boolean disableLoadImage,boolean headless,String userAgent) {
		HttpsProxy googleProxy = MitmServer.getInstance().getGoogleMitmProxy();
		log.info("set googleProxy:"+JSON.toJSONString(googleProxy));
		return new ChromeAjaxHookDriver(ChromeOptionsUtil.createChromeOptions(disableLoadImage, headless, googleProxy, userAgent));
	}
	
	public ChromeAjaxHookDriver(ChromeOptions options) {
		super(options);
		cloudIdValue = (String) options.getCapability(ChromeOptionsUtil.USER_AGENTID);
		manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);//脚步执行超时
		manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);//页面加载超时
	}
	
	public void addAjaxHook(AjaxHook hook) {
		if (hook != null) {
			MitmServer.getInstance().addAjaxHook(cloudIdValue, hook);
		}
	}
	
	public void hideElement(WebElement elm) {
		if (elm != null) {
			executeScript("arguments[0].style='display:none;';", elm);
		}
	}

	@Override
	public void get(String url) {
		for (int i = 0 ; i < 3 ; i++) {
			try {
				super.get(url);
				log.info("visit:"+url);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			break;
		}
	}
	
	public void getIgnoreTimeout(String url) {
		try {
			super.get(url);
			log.info("visit:"+url);
		}catch(Exception e){
		}
	}
	
	public boolean checkElement(String cssSelect) {
		try {
			return findElementByCssSelector(cssSelect).isDisplayed();
		}catch(Exception e) {
		}
		return false;
	}
	
	private void sleep(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getPageSource() {
		String ret = super.getPageSource();
		Matcher matcher = Pattern.compile("<html><head></head>.*wrap;\">").matcher(ret);
		if (matcher.find()){
			ret = ret.replace(matcher.group(), "").replace("</pre></body></html>", "");
		}else if (ret.startsWith("<html><head></head><body>")) {
			ret = ret.replace("<html><head></head><body>", "").replace("</body></html>", "");
		}
		return ret;
	}
	

	@Override
	public void close() {
		try {
			MitmServer.getInstance().removeHooks(cloudIdValue);Thread.sleep(1000);
			super.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void quit() {
		try {
			MitmServer.getInstance().removeHooks(cloudIdValue);Thread.sleep(1000);
			super.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean switchSlide(WebElement rdsSlideReset, WebElement rdsSlideBtn) {
		try {
			//manage().window().maximize();
			//manage().window().fullscreen();
			int width = rdsSlideReset.getRect().width;
			Actions actions = new Actions(this);
			try {
				actions.moveToElement(rdsSlideBtn).perform();Thread.sleep(random.nextInt(1500));
			} catch (Exception e) {
				e.printStackTrace();
			}Thread.sleep(random.nextInt(1500));
			actions.clickAndHold(rdsSlideBtn).perform();
			int toWidth = 0;
			int floatY = 0;
			while (true) {
				int xOffset = random.nextInt(25) + 1;
				int fy = random.nextInt(3);
				if (floatY > 10) {
					fy = - fy;
				}
				floatY += fy;
				actions.moveByOffset(xOffset, floatY).perform();
				toWidth += xOffset;
				if (toWidth > width) {Thread.sleep(random.nextInt(50));
					actions.release().perform();
					break;
				}Thread.sleep(random.nextInt(100));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public byte[] screenshot(WebElement webElement) throws Exception {
		return webElement.getScreenshotAs(OutputType.BYTES);
	}
	
	public byte[] screenshot() throws Exception {
		return getScreenshotAs(OutputType.BYTES);
	}
	
	public void mouseClick(WebElement webElement) throws Exception {
		Actions actions = new Actions(this);
		actions.moveToElement(webElement).perform();Thread.sleep(random.nextInt(800));
		actions.clickAndHold(webElement).perform();Thread.sleep(random.nextInt(300));
		actions.release().perform();Thread.sleep(random.nextInt(1500));
	}
	
	public void keyboardClear(WebElement webElement, int backSpace) throws Exception {
		mouseClick(webElement);
		for (int k = 0; k < backSpace + random.nextInt(3); k++) {
			webElement.sendKeys(Keys.BACK_SPACE);Thread.sleep(random.nextInt(100));
		}
	}
	
	public void keyboardInput(WebElement webElement, String text) throws Exception {
		keyboardInput(webElement, text, true);
	}
	
	public void keyboardInput(WebElement webElement, String text, boolean needClick) throws Exception {
		if (needClick)
			mouseClick(webElement);
		int inputed = 0;
		int backTimes = 0;
		int prebackNums = random.nextInt(text.length() / 3);
		for (int k = 0; k < text.length(); k++) {
			webElement.sendKeys(String.valueOf(text.charAt(k)));Thread.sleep(random.nextInt(300) + 300);
			inputed ++;
			int backNum = inputed >= 3 && backTimes <= prebackNums ?random.nextInt(3) : 0;
			backTimes += backNum;
			for (int i = 0; i < backNum; i++) {
				webElement.sendKeys(Keys.BACK_SPACE);Thread.sleep(random.nextInt(300) + 300);
				k--;
			}
		}
	}
	
	public void jsInput(WebElement webElement, String text) throws Exception {
		executeScript("arguments[0].value='"+text+"';", webElement);
	}
	
	public void jsClick(WebElement webElement) throws Exception {
		jsClick(webElement, false);
	}
	
	public void jsClick(WebElement webElement, boolean async) throws Exception {
		if (async) {
			try {
				executeAsyncScript("arguments[0].click();", webElement);Thread.sleep(1000);
			} catch (Exception e) {
				log.warn("js点击出错", e);
			}
		}else {
			executeScript("arguments[0].click();", webElement);
		}
	}

	public boolean switchToTab(String containsTitleChars) {
		if (getTitle().contains(containsTitleChars)) {
			return true;
		}
		String currentHandlerId = getWindowHandle();
		for (String handlerId : getWindowHandles()) {
			if (handlerId.equals(currentHandlerId)) {
				continue;
			}
			String title = getTitle();
			if (title.contains(containsTitleChars)) {
				return true;
			}
		}
		return false;
	}
}
