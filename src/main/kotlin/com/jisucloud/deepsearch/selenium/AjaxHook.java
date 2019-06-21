package com.jisucloud.deepsearch.selenium;

import java.util.Map;

public interface AjaxHook {
	
	public String matcherUrl();
	
	public void beforeGet(String url,Map<String,String> headers);
	
	public void beforePost(String url,Map<String,String> headers,String postData);
	
	public void afterPost(String url,Map<String,String> headers,String postData,String response);
	
	public void afterGet(String url,Map<String,String> headers,String response);
}
