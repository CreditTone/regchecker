package com.jisucloud.clawler.regagent.mitm;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.deep007.goniub.selenium.mitm.cache.MitmCacheProvider;

public class StringRedisTemplateMitmCacheProvider extends MitmCacheProvider {
	
	private final StringRedisTemplate template;
	
	public StringRedisTemplateMitmCacheProvider(StringRedisTemplate template) {
		this.template = template;
	}

	@Override
	public void setCache(String url, String contents) {
		template.opsForValue().set(getKey(url), contents, cacheTime, TimeUnit.SECONDS);
	}

	@Override
	public String getCache(String url) {
		return template.opsForValue().get(getKey(url));
	}

}
