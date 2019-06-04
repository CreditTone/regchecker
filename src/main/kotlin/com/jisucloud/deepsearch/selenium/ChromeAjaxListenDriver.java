package com.jisucloud.deepsearch.selenium;

import java.util.Random;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChromeAjaxListenDriver extends ChromeDriver implements Runnable{
	
	public static final Random random = new Random();
	
	private AjaxListener ajaxListener;
	
	private Thread readAjaxQueueThread;
	
	public ChromeAjaxListenDriver(ChromeOptions options) {
		super(options);
	}

	@Override
	public void get(String url) {
		super.get(url);
		log.info("visit:"+url);
		if (ajaxListener != null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			executeScript(AjaxListererJs.ArrayQueueJS);
			log.info("ArrayQueueJS:"+url);
			executeScript(AjaxListererJs.AjaxListeneJS);
			log.info("AjaxListeneJS:"+url);
		}
	}
	
	public void quicklyVisit(String url) {
		try {
			super.get(url);
		}catch(Exception e) {
			log.warn("quicklyVisit error "+e.getLocalizedMessage());
		}
	}
	
	public boolean isXHRListener() {
		Object ret = null;
		try {
			String script = "return window.myQueue != undefined;";
			ret = executeScript(script);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		}
		return ret != null? (Boolean)ret : false;
	}
	
	private Ajax takeAjax() {
		Ajax ajax = null;
		String ret = null;
		try {
			ret = (String) executeScript(AjaxListererJs.ajaxGetJs);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if (ret != null && ret.length() > 20) {
			JSONObject result = JSON.parseObject(ret);
			ajax = new Ajax();
			ajax.setUrl(result.getString("requestUrl"));
			ajax.setMethod(result.getString("requestMethod"));
			ajax.setRequestData(result.getString("requestData"));
			ajax.setResponse(result.getString("responseText"));
		}
		return ajax;
	}
	
	public void setAjaxListener(AjaxListener ajaxListener) {
		if (this.ajaxListener != null && isXHRListener()) {
			this.ajaxListener = ajaxListener;
		}else {
			this.ajaxListener = ajaxListener;
		}
		if (readAjaxQueueThread != null) {
			try {readAjaxQueueThread.stop();}catch(Exception e) {}
		}
		readAjaxQueueThread = new Thread(this);
		readAjaxQueueThread.start();
		if (getCurrentUrl().startsWith("http")) {
			get(getCurrentUrl());
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
		while (ajaxListener != null) {
			ajax = takeAjax();
			if (ajax != null) {
				System.out.println(ajax);
			}
			if (ajax != null && ajax.getUrl().contains(ajaxListener.matcherUrl())) {
				try {
					ajaxListener.ajax(ajax);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void close() {
		if (readAjaxQueueThread != null) {
			try {readAjaxQueueThread.stop();}catch(Exception e) {}
			readAjaxQueueThread = null;
		}
		super.close();
	}

	@Override
	public void quit() {
		if (readAjaxQueueThread != null) {
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
		mouseClick(webElement);
		int inputed = 0;
		int backTimes = 0;
		int prebackNums = random.nextInt(text.length() / 3);
		for (int k = 0; k < text.length(); k++) {
			webElement.sendKeys(String.valueOf(text.charAt(k)));
			Thread.sleep(random.nextInt(1000) + 500);
			inputed ++;
			int backNum = inputed >= 3 && backTimes <= prebackNums ?random.nextInt(3) : 0;
			backTimes += backNum;
			for (int i = 0; i < backNum; i++) {
				webElement.sendKeys(Keys.BACK_SPACE);
				Thread.sleep(random.nextInt(1530) + 500);
				k--;
			}
		}
	}
}
