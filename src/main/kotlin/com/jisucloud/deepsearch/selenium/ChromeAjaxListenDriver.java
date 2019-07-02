package com.jisucloud.deepsearch.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public class ChromeAjaxListenDriver extends ChromeDriver implements Runnable{
	
	public static final Random random = new Random();
	
	private List<AjaxListener> ajaxListeners = new ArrayList<>();
	
	private Thread readAjaxQueueThread;
	
	private boolean quited = false;
	
	private Map<String,Long> respTime = new ConcurrentHashMap<>();
	
	public ChromeAjaxListenDriver(ChromeOptions options) {
		super(options);
		readAjaxQueueThread = new Thread(this);
		readAjaxQueueThread.start();
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
			reInject();
			break;
		}
		reInject();
	}
	
	public void quicklyVisit(String url) {
		for (int i = 0 ; i < 3 ; i++) {
			try {
				super.get(url);
				log.info("quicklyVisit:"+url);
			}catch(Exception e){
				continue;
			}
			break;
		}
	}
	
	public boolean isXHRListener(String id) {
		Object ret = null;
		try {
			String script = "return window.injectedListener != undefined && window.injectedListener['"+id+"'] != undefined;";
			ret = executeScript(script);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		}
		return ret != null? (Boolean)ret : false;
	}
	
	public boolean isXHRMainListener() {
		Object ret = null;
		try {
			String script = "return window.injectedListener != undefined;";
			ret = executeScript(script);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		}
		return ret != null? (Boolean)ret : false;
	}
	
	public boolean checkElement(String cssSelect) {
		try {
			return findElementByCssSelector(cssSelect).isDisplayed();
		}catch(Exception e) {
		}
		return false;
	}
	
	private Ajax takeAjax() {
		Ajax ajax = null;
		String ret = null;
		try {
			ret = (String) executeScript(AjaxListererJs.ajaxGetJs);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if (ret != null && !ret.isEmpty()) {
			Long reciveRespTime = respTime.get(ret);
			if (reciveRespTime == null) {
				reciveRespTime = System.currentTimeMillis();
				respTime.put(ret, reciveRespTime);
			}else if ((System.currentTimeMillis() - reciveRespTime) < 3000) {
				return null;
			}else {
				respTime.put(ret, System.currentTimeMillis());
			}
			JSONObject result = JSON.parseObject(ret);
			ajax = new Ajax();
			ajax.setUrl(result.getString("requestUrl"));
			ajax.setMethod(result.getString("requestMethod"));
			ajax.setRequestData(result.getString("requestData"));
			ajax.setResponse(StringUtil.unicodeToString(result.getString("responseText")));
			//System.out.println(ajax);
		}
		return ajax;
	}
	
	@Deprecated
	public void setAjaxListener(AjaxListener ajaxListener) {
		ajaxListeners.add(ajaxListener);
		if (getCurrentUrl().startsWith("http")) {
			reInject();
		}
	}
	
	public void addAjaxListener(AjaxListener ajaxListener) {
		ajaxListeners.add(ajaxListener);
		if (getCurrentUrl().startsWith("http")) {
			reInject();
		}
	}
	
	private void sleep(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void reInject() {
		if (ajaxListeners.isEmpty()) {
			return;
		}
		if (!isXHRMainListener()) {
			executeScript(AjaxListererJs.ArrayQueueJS);
			log.info("arrayqueue inject success");
			executeScript(AjaxListererJs.OprationVarJS);
			log.info("oprationvarjs inject success");
			executeScript(AjaxListererJs.ReplaceXHRJS);
			log.info("replacexhrjs inject success");
			executeScript(AjaxListererJs.AjaxListeneJS);
			log.info("ajaxlistenejs inject success");
			executeScript(AjaxListererJs.AjaxPushResultJS);
			log.info("ajaxpushresultjs inject success");
		}
		for (AjaxListener ajaxListener : ajaxListeners) {
			injectedListener(ajaxListener);
		}
	}
	
	
	private void injectedListener(AjaxListener ajaxListener) {
		String id = Integer.toHexString(ajaxListener.hashCode());
		if (!isXHRListener(id)) {
			sleep(300);
			executeScript("window.injectedListener['"+ id +"'] = '"+ajaxListener.matcherUrl()+"';");
			for (int i = 0;ajaxListener.blockUrl() != null && i < ajaxListener.blockUrl().length; i++) {
				executeScript("window.blockAjax.push('"+ ajaxListener.blockUrl()[i] +"');");
			}
			if (ajaxListener.fixPostData() != null) {
				executeScript("window.fixPostData['"+ ajaxListener.matcherUrl() +"'] = '"+ajaxListener.fixPostData()+"';");
			}
			if (ajaxListener.fixGetData() != null) {
				executeScript("window.fixGetData['"+ ajaxListener.matcherUrl() +"'] = '"+ajaxListener.fixGetData()+"';");
			}
			sleep(300);
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
	public void run() {
		Ajax ajax = null;
		while (!quited) {
			ajax = takeAjax();
			if (ajax != null) {
				processAjax(ajax);
			}
		}
		
	}
	
	private void processAjax(Ajax ajax) {
		for (AjaxListener ajaxListener : ajaxListeners) {
			if (ajax != null) {
				if (ajax.getUrl().contains(ajaxListener.matcherUrl()) || ajaxListener.matcherUrl().contains(ajax.getUrl())) {
					try {
						ajaxListener.ajax(ajax);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void close() {
		quited = true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (readAjaxQueueThread != null && readAjaxQueueThread.isAlive()) {
			try {readAjaxQueueThread.stop();}catch(Exception e) {}
			readAjaxQueueThread = null;
		}
		super.close();
	}

	@Override
	public void quit() {
		quited = true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (readAjaxQueueThread != null && readAjaxQueueThread.isAlive()) {
			try {readAjaxQueueThread.stop();}catch(Exception e) {}
			readAjaxQueueThread = null;
		}
		super.quit();
	}
	
	public byte[] screenshot(WebElement webElement) throws Exception {
		byte[] body = webElement.getScreenshotAs(OutputType.BYTES);
		return body;
	}
	
	public void mouseClick(WebElement webElement) throws Exception {
		Actions actions = new Actions(this);
		actions.moveToElement(webElement).perform();
		Thread.sleep(random.nextInt(1500));
		actions.click().perform();
		Thread.sleep(random.nextInt(1500));
	}
	
	public void keyboardClear(WebElement webElement, int backSpace) throws Exception {
		mouseClick(webElement);
		for (int k = 0; k < backSpace + random.nextInt(3); k++) {
			webElement.sendKeys(Keys.BACK_SPACE);
			Thread.sleep(random.nextInt(150));
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
			webElement.sendKeys(String.valueOf(text.charAt(k)));
			Thread.sleep(random.nextInt(500) + 300);
			inputed ++;
			int backNum = inputed >= 3 && backTimes <= prebackNums ?random.nextInt(3) : 0;
			backTimes += backNum;
			for (int i = 0; i < backNum; i++) {
				webElement.sendKeys(Keys.BACK_SPACE);
				Thread.sleep(random.nextInt(500) + 300);
				k--;
			}
		}
	}
	
	public void jsInput(WebElement webElement, String text) throws Exception {
		executeScript("arguments[0].value='"+text+"';", webElement);
	}
}
