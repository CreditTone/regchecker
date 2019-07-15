package com.jisucloud.deepsearch.selenium.mitm;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
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
import net.lightbody.bmp.proxy.auth.AuthType;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

@Slf4j
public class MitmServer implements RequestFilter, ResponseFilter {

	/**
	 * 翻墙代理
	 */
	public static final String GOOGLE_PROXY = "GOOGLE_PROXY";

	/**
	 * 本地代理
	 */
	public static final String LOCAL_PROXY = "LOCAL_PROXY";

	/**
	 * 国内随机出网代理
	 */
	public static final String RANDOM_PROXY = "RANDOM_PROXY";

	private static Object locker = new Object();

	private Pattern cloudIdPattern = Pattern.compile("\\s+Cloud/([a-z0-9]+)");

	private static MitmServer instance;

	/**
	 * 各种性质的代理
	 */
	private Map<String, BrowserMobProxy> proxyServers = new HashMap<>();

	private Map<String, List<AjaxHook>> hookers = new ConcurrentHashMap<>();

	private MitmServer() {
	}

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
		// 本地代理
		BrowserMobProxy proxyServer = new BrowserMobProxyServer();
		proxyServer.blacklistRequests("google\\.com.*", 500);
		proxyServer.setTrustAllServers(true);
		proxyServer.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		proxyServer.addRequestFilter(this);
		proxyServer.addResponseFilter(this);
		try {
			proxyServer.start(8120, InetAddress.getByName("0.0.0.0"));
			proxyServers.put(LOCAL_PROXY, proxyServer);
			log.info("本地劫持代理启动成功");
		} catch (Exception e) {
			log.info("本地劫持代理启动失败", e);
		}

		// 国内随机代理
		BrowserMobProxy randomProxyServer = new BrowserMobProxyServer();
		randomProxyServer.blacklistRequests("google\\.com.*", 500);
		randomProxyServer.setTrustAllServers(true);
		randomProxyServer.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		randomProxyServer.addRequestFilter(this);
		randomProxyServer.addResponseFilter(this);
		randomProxyServer.setChainedProxy(InetSocketAddress.createUnresolved("http-dyn.abuyun.com", 9020));
		randomProxyServer.chainedProxyAuthorization("H6224X2YF291C2AD", "2EADA65DEE87F60C", AuthType.BASIC);
		try {
			randomProxyServer.start(8121, InetAddress.getByName("0.0.0.0"));
			proxyServers.put(RANDOM_PROXY, randomProxyServer);
			log.info("国内随机劫持代理启动成功");
		} catch (Exception e) {
			log.info("国内随机劫持代理启动失败", e);
		}

		// 翻墙代理
		BrowserMobProxy googleProxyServer = new BrowserMobProxyServer();
		googleProxyServer.setTrustAllServers(true);
		googleProxyServer.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		googleProxyServer.addRequestFilter(this);
		googleProxyServer.addResponseFilter(this);
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			googleProxyServer.setChainedProxy(InetSocketAddress.createUnresolved("127.0.0.1", 64445));
        }else {
        		googleProxyServer.setChainedProxy(InetSocketAddress.createUnresolved("5d23158e0d319529.natapp.cc", 64445));
        }
		try {
			googleProxyServer.start(8122, InetAddress.getByName("0.0.0.0"));
			proxyServers.put(GOOGLE_PROXY, googleProxyServer);
			log.info("翻墙劫持代理启动成功");
		} catch (Exception e) {
			log.info("翻墙劫持代理启动失败", e);
		}
	}
	
	public HttpsProxy getGoogleMitmProxy() {
		if (!proxyServers.containsKey(GOOGLE_PROXY)) {
			return null;
		}
		return new HttpsProxy("127.0.0.1", proxyServers.get(GOOGLE_PROXY).getPort());
	}
	
	public HttpsProxy getRandomMitmProxy() {
		if (!proxyServers.containsKey(RANDOM_PROXY)) {
			return null;
		}
		return new HttpsProxy("127.0.0.1", proxyServers.get(RANDOM_PROXY).getPort());
	}
	
	public HttpsProxy getLocalMitmProxy() {
		if (!proxyServers.containsKey(LOCAL_PROXY)) {
			return null;
		}
		return new HttpsProxy("127.0.0.1", proxyServers.get(LOCAL_PROXY).getPort());
	}

	public synchronized void stop() {
		for (BrowserMobProxy browserMobProxy : proxyServers.values()) {
			if (browserMobProxy != null && browserMobProxy.isStarted()) {
				browserMobProxy.stop();
			}
		}
	}

	public void addAjaxHook(String hookIdValue, AjaxHook hooker) {
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
		// System.out.println("------------------------request-------------------------");
		// System.out.println("url:" + messageInfo.getOriginalUrl());
		// System.out.println("method:" + messageInfo.getOriginalRequest().method());
		// System.out.println("textContents:" + contents.getTextContents());
		// log.info("hook requst:" + messageInfo.getOriginalUrl());
		if (messageInfo.getOriginalUrl().contains("accounts.google.com")) {
			return new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.NO_CONTENT);
		}
		HttpHeaders headers = messageInfo.getOriginalRequest().headers();
		String cloudId = extractCloudId(headers);
		if (cloudId == null) {
			// log.info("noheaders hook request:" + messageInfo.getOriginalUrl());
			return null;
		}
		List<AjaxHook> results = getHookers(cloudId);
		if (results != null) {
			HttpResponse httpResponse = null;
			for (AjaxHook ajaxHook : results) {
				if (ajaxHook.getHookTracker() == null
						|| ajaxHook.getHookTracker().isHookTracker(contents, messageInfo, 1)) {
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
			// log.info("noheaders hook response:" + messageInfo.getOriginalUrl());
			return;
		}
		List<AjaxHook> results = getHookers(cloudId);
		if (results != null) {
			for (AjaxHook ajaxHook : results) {
				if (ajaxHook.getHookTracker() == null
						|| ajaxHook.getHookTracker().isHookTracker(contents, messageInfo, 2)) {
					log.info("hook response:" + messageInfo.getOriginalUrl());
					ajaxHook.filterResponse(response, contents, messageInfo);
				}
			}
		}
	}
}
