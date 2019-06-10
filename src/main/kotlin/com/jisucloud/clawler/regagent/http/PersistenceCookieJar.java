package com.jisucloud.clawler.regagent.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Cookie;
import okhttp3.Cookie.Builder;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

@Slf4j
public class PersistenceCookieJar implements CookieJar {
	
	private Map<String,List<Cookie>> domainToCookies = new ConcurrentHashMap<>();
	
	public void saveCookies(String url,Set<org.openqa.selenium.Cookie> cookies) {
		//log.info("saveCookies:"+cookies.toString());
		List<Cookie> cache = new ArrayList<>();
		Iterator<org.openqa.selenium.Cookie> iter = cookies.iterator();
		while (iter.hasNext()) {
			org.openqa.selenium.Cookie cookie = iter.next();
			String domain = cookie.getDomain();
			if (domain.startsWith(".")) {
				domain = domain.substring(1);
			}
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
		}
		HttpUrl httpUrl = HttpUrl.get(url);
		saveFromResponse(httpUrl, cache);
	}

	@Override
	public List<Cookie> loadForRequest(HttpUrl url) {
		List<Cookie> cache = domainToCookies.getOrDefault(url.host(), new ArrayList<Cookie>());
		return cache;
	}

	@Override
	public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		domainToCookies.remove(url.host());
		List<Cookie> cache = domainToCookies.getOrDefault(url.host(), new ArrayList<Cookie>());
		cache.addAll(cookies);
		domainToCookies.put(url.host(), cache);
	}

}
