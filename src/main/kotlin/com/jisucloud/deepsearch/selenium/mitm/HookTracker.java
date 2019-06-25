package com.jisucloud.deepsearch.selenium.mitm;

import java.util.HashSet;
import java.util.Set;

import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

@Builder(builderClassName= "HookTrackerBuilder")
@Data
@NoArgsConstructor
@AllArgsConstructor 
@Slf4j
public class HookTracker {

	private Set<String> urls;
	
	private String method;
	
	public boolean isHookTracker(HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String originalUrl = messageInfo.getOriginalUrl();
		String rmethod = messageInfo.getOriginalRequest().method().name();
		if (method != null && !method.equalsIgnoreCase(rmethod)) {
			return false;
		}
		for (String url : urls) {
			if (url.contains(originalUrl) || originalUrl.contains(url)) {
				return true;
			}
		}
		//log.warn("false urls:"+urls+" originalUrl:"+originalUrl);
		return false;
	}
	
	public static class HookTrackerBuilder {
		
		private Set<String> urls = new HashSet<>();
		
		public HookTrackerBuilder addUrl(String url) {
			if (StringUtil.isValidString(url)) {
				urls.add(url);
			}
			return this;
		}
		
		public HookTrackerBuilder isGet() {
			method = "GET";
			return this;
		}
		
		public HookTrackerBuilder isAny() {
			method = null;
			return this;
		}
		
		public HookTrackerBuilder isPOST() {
			method = "POST";
			return this;
		}
	}
}
