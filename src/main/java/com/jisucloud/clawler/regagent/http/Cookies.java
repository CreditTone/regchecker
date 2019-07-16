package com.jisucloud.clawler.regagent.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cookies {
	
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
	public Cookies() {
	}
	
	public Cookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}
	
	public void addCookie(Cookie cookie){
		cookies.add(cookie);
	}
	
	public Cookies merge(Cookies cookies) {
		Iterator<Cookie> iter = cookies.iterator();
		while(iter.hasNext()) {
			this.cookies.add(iter.next());
		}
		return this;
	}
	
	public Iterator<Cookie> iterator(){
		return cookies.iterator();
	}
	
	@Override
	public String toString() {
		return "Cookies [cookies=" + cookies + "]";
	}

}
