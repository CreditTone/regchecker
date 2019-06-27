package com.jisucloud.clawler.regagent.http;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

@Service
public class CookieService extends Thread {

	public static String BENLAI_COOKIE = "benlai88592";

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	private static CookieService instance;

	@PostConstruct
	public void startThread() {
		instance = this;
		start();
	}
	
	public static CookieService getInstance() {
		return instance;
	}

	public Cookies getCookies(String key) {
		Cookies store = new Cookies();
		try {
			if (stringRedisTemplate.hasKey(key)) {
				return store;
			}
			JSONArray baiduCookies = JSON.parseArray(stringRedisTemplate.boundValueOps(key).get());
			for (int i = 0; baiduCookies != null && i < baiduCookies.size(); i++) {
				JSONObject cookie = baiduCookies.getJSONObject(i);
				Cookie item = new Cookie(cookie.getString("name"),
						cookie.getString("value"), cookie.getString("domain"), cookie.getString("path"),
						cookie.containsKey("expiry") ? new Date(cookie.getLongValue("expiry")) : null,
						cookie.getBooleanValue("isSecure"), cookie.getBooleanValue("isHttpOnly"));
				store.addCookie(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return store;
	}
	
	public long ttl(String key) throws Exception {
		Exception e = null;
		long ret = -1;
		for (int i = 0; i < 5; i++) {
			try {
				ret = stringRedisTemplate.boundValueOps(key).getExpire();
			} catch (Exception ex) {
				ex.printStackTrace();
				e = ex;
			} finally {
			}
			if (e != null) {
				throw e;
			}
			if (ret != -1) {
				break;
			}
		}
		
		return ret;
	}

	public void setex(final String key, final int seconds, final String value) {
		if ("null".equals(value)) {
			return;
		}
		try {
			stringRedisTemplate.boundValueOps(key).set(value, Duration.ofSeconds(seconds));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
