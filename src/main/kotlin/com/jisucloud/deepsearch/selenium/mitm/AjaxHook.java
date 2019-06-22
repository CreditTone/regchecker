package com.jisucloud.deepsearch.selenium.mitm;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

public interface AjaxHook {
	
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo);
	
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo);
	
}
