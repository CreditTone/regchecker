package com.jisucloud.deepsearch.selenium;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChromeAjaxListenDriver extends ChromeDriver implements Runnable{
	
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
	
	
}
