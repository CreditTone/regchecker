package com.jisucloud.deepsearch.selenium;

public interface AjaxListener {
	
	public String matcherUrl();

	public void ajax(Ajax ajax) throws Exception;
	
}
