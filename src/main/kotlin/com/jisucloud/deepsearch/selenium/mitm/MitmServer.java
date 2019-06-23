package com.jisucloud.deepsearch.selenium.mitm;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jisucloud.deepsearch.selenium.HttpsProxy;

import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

@Slf4j
public class MitmServer implements RequestFilter,ResponseFilter {
	
	public static void main(String[] args) {
		MitmServer.getInstance();
	}
	
	private static Object locker = new Object();
	
	private Pattern cloudIdPattern = Pattern.compile("\\s+Cloud/([a-z0-9]+)");
	
	private static final int BIND_PORT = 8119;
	
	private static MitmServer instance;
	
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
		proxy.blacklistRequests("google\\.com.*", 500);
		proxy.setTrustAllServers(true);
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		proxy.addRequestFilter(this);
		log.info("addRequestFilter");
		proxy.addResponseFilter(this);
		log.info("addResponseFilter");
		try {
			proxy.start(BIND_PORT, InetAddress.getByName("0.0.0.0"));
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
		if (hookIdValue != null && hookers.containsKey(hookIdValue)) {
			hookers.remove(hookIdValue);
		}
	}
	
	private List<AjaxHook> getHookers(String hookIdValue) {
		List<AjaxHook> results = hookers.get(hookIdValue);
		return results;
	}
	
	/**
	 * 
	 * @return
	 */
	private String extractCloudId(String ua) {
		if (ua == null) {
			return null;
		}
		Matcher matcher = cloudIdPattern.matcher(ua);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	
	private String extractCloudId(HttpHeaders headers) {
		String userAgent = headers.get("User-Agent");
		if (userAgent == null) {
			userAgent = headers.get("user-agent");
		}
		return extractCloudId(userAgent);
	}
	
	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
//		System.out.println("------------------------request-------------------------");
//		System.out.println("url:" + messageInfo.getOriginalUrl());
//		System.out.println("method:" + messageInfo.getOriginalRequest().method());
//		System.out.println("textContents:" + contents.getTextContents());
		//log.info("hook requst:" + messageInfo.getOriginalUrl());
		if (messageInfo.getOriginalUrl().contains("accounts.google.com")) {
			return new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.NO_CONTENT);
		}
		HttpHeaders headers = messageInfo.getOriginalRequest().headers();
		String cloudId = extractCloudId(headers);
		if (cloudId == null) {
			//log.info("noheaders hook request:" + messageInfo.getOriginalUrl());
			return null;
		}
		List<AjaxHook> results = getHookers(cloudId);
		if (results != null) {
			HttpResponse httpResponse = null;
			for (AjaxHook ajaxHook : results) {
				if (ajaxHook.getHookTracker() == null || ajaxHook.getHookTracker().isHookTracker(contents, messageInfo)) {
					log.info("hook requst:" + messageInfo.getOriginalUrl());
					httpResponse = ajaxHook.filterRequest(request, contents, messageInfo);
				}
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
		String cloudId = extractCloudId(headers);
		if (cloudId == null) {
			//log.info("noheaders hook response:" + messageInfo.getOriginalUrl());
			return;
		}
		List<AjaxHook> results = getHookers(cloudId);
		if (results != null) {
			for (AjaxHook ajaxHook : results) {
				if (ajaxHook.getHookTracker() == null || ajaxHook.getHookTracker().isHookTracker(contents, messageInfo)) {
					log.info("hook response:" + messageInfo.getOriginalUrl());
					ajaxHook.filterResponse(response, contents, messageInfo);
				}
			}
		}
	}
}
