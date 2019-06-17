package com.jisucloud.deepsearch.selenium;

public interface AjaxListener {
	
	public String matcherUrl();
	
	public String fixGetData();
	
	public String fixPostData();
	
	public String[] blockUrl();

	public void ajax(Ajax ajax) throws Exception;
	
}
