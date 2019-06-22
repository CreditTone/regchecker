package com.jisucloud.deepsearch.selenium.mitm;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jisucloud.deepsearch.selenium.HttpsProxy;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

public class MitmServer implements RequestFilter,ResponseFilter {
	
	private static Object locker = new Object();
	
	private static final int BIND_PORT = 8119;
	
	public static MitmServer instance;
	
	private BrowserMobProxy proxy = null;
	
	private Map<String,List<AjaxHook>> hookers = new ConcurrentHashMap<>();
	
	private MitmServer() {}
	
	public static MitmServer getInstance() {
		if (instance == null) {
			synchronized (locker) {
				if (instance == null) {
					instance = new MitmServer();
					instance.start();
				}
			}
		}
		return instance;
	}
	
	private synchronized void start() {
		if (proxy != null && proxy.isStarted()) {
			return;
		}
		proxy = new BrowserMobProxyServer();
		proxy.setTrustAllServers(true);
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		try {
			proxy.start(BIND_PORT, InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public HttpsProxy getHttpsProxy() {
		return new HttpsProxy("127.0.0.1", BIND_PORT);
	}
	
	public synchronized void stop() {
		if (proxy != null && proxy.isStarted()) {
			proxy.stop();
			proxy = null;
		}
	}
	
	public void addAjaxHook(String hookIdValue,AjaxHook hooker) {
		List<AjaxHook> results = hookers.get(hookIdValue);
		if (results == null) {
			results = new ArrayList<>();
			hookers.put(hookIdValue, results);
		}
		results.add(hooker);
	}
	
	public void removeHooks(String hookIdValue) {
		hookers.remove(hookIdValue);
	}
	
	private List<AjaxHook> getHookers(String hookIdValue) {
		List<AjaxHook> results = hookers.get(hookIdValue);
		return results;
	}
	
	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		System.out.println("------------------------request-------------------------");
		System.out.println("url:" + messageInfo.getOriginalUrl());
		System.out.println("isHttps:" + messageInfo.isHttps());
		System.out.println("method:" + messageInfo.getOriginalRequest().method());
		System.out.println("headers:" + messageInfo.getOriginalRequest().headers());
		System.out.println("contentType:" + contents.getContentType());
		System.out.println("textContents:" + contents.getTextContents());
		System.out.println("decoderResult:" + messageInfo.getOriginalRequest().decoderResult().toString());
		HttpHeaders headers = messageInfo.getOriginalRequest().headers();
		if (headers == null || headers.get(ChromeAjaxHookDriver.hookIdName) == null) {
			return null;
		}
		String hookIdValue = headers.get(ChromeAjaxHookDriver.hookIdName);
		List<AjaxHook> results = getHookers(hookIdValue);
		if (results != null) {
			HttpResponse httpResponse = null;
			for (AjaxHook ajaxHook : results) {
				httpResponse = ajaxHook.filterRequest(request, contents, messageInfo);
				if (httpResponse != null) {
					return httpResponse;
				}
			}
		}
		return null;
	}
	
	
	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		HttpHeaders headers = messageInfo.getOriginalRequest().headers();
		if (headers == null || headers.get(ChromeAjaxHookDriver.hookIdName) == null) {
			return;
		}
		String hookIdValue = headers.get(ChromeAjaxHookDriver.hookIdName);
		List<AjaxHook> results = getHookers(hookIdValue);
		if (results != null) {
			for (AjaxHook ajaxHook : results) {
				ajaxHook.filterResponse(response, contents, messageInfo);
			}
		}
	}
}
