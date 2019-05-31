package com.jisucloud.clawler.regagent.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jisucloud.clawler.regagent.util.HeadlessUtil;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Cookie;
import okhttp3.Cookie.Builder;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

@Slf4j
public class PersistenceCookieJar implements CookieJar {
	
	private Map<String,Set<Cookie>> domainToCookies = new ConcurrentHashMap<>();
	
	public void saveCookies(Cookies store) {
		Iterator<com.jisucloud.clawler.regagent.service.Cookie> iter = store.iterator();
		while (iter.hasNext()) {
			com.jisucloud.clawler.regagent.service.Cookie cookie = iter.next();
			String domain = StringUtil.getDomain(cookie.getDomain());
			Set<Cookie> cache = domainToCookies.getOrDefault(domain, new HashSet<Cookie>());
			Builder cookieBuilder = new Cookie.Builder().domain(domain).name(cookie.getName()).value(cookie.getValue()).path(cookie.getPath());
			if (cookie.isHttpOnly()) {
				cookieBuilder.httpOnly();
			}
			if (cookie.isSecure()) {
				cookieBuilder.secure();
			}
			if (cookie.getExpiry() != null) {
				cookieBuilder.expiresAt(cookie.getExpiry().getTime());
			}
			cache.add(cookieBuilder.build());
			domainToCookies.put(domain, cache);
		}
	}
	
	public void saveCookies(Set<org.openqa.selenium.Cookie> cookies) {
		log.info("saveCookies:"+cookies.toString());
		Iterator<org.openqa.selenium.Cookie> iter = cookies.iterator();
		while (iter.hasNext()) {
			org.openqa.selenium.Cookie cookie = iter.next();
			String domain = StringUtil.getDomain(cookie.getDomain());
			Set<Cookie> cache = domainToCookies.getOrDefault(domain, new HashSet<Cookie>());
			Builder cookieBuilder = new Cookie.Builder().domain(domain).name(cookie.getName()).value(cookie.getValue()).path(cookie.getPath());
			if (cookie.isHttpOnly()) {
				cookieBuilder.httpOnly();
			}
			if (cookie.isSecure()) {
				cookieBuilder.secure();
			}
			if (cookie.getExpiry() != null) {
				cookieBuilder.expiresAt(cookie.getExpiry().getTime());
			}
			cache.add(cookieBuilder.build());
			domainToCookies.put(domain, cache);
		}
	}

	@Override
	public List<Cookie> loadForRequest(HttpUrl url) {
		String domain = StringUtil.getDomain(url.host());
		Set<Cookie> cache = domainToCookies.getOrDefault(domain, new HashSet<Cookie>());
		System.out.println(url +" get:"+cache);
		return new ArrayList<>();
	}

	@Override
	public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		String domain = StringUtil.getDomain(url.host());
		Set<Cookie> cache = domainToCookies.getOrDefault(domain, new HashSet<Cookie>());
		cache.addAll(cookies);
		System.out.println("save:"+cache);
		domainToCookies.put(domain, cache);
	}

}
