package com.jisucloud.deepsearch.selenium.mitm;

public class AjaxHookJs {
	
	public static final String getHookAjaxJs(String name,String value) {
		String hookAjaxJs = HookAjaxJs
				.replace("window.hookIdName = undefined;", "window.hookIdName = '"+name+"';")
				.replace("window.hookIdValue = undefined;", "window.hookIdValue = '"+value+"';");
		return hookAjaxJs;
	}
	
	public static final String HookAjaxJs = "window.hooked = '1';\n" +
			"window.hookIdName = undefined;\n" + 
			"window.hookIdValue = undefined;\n" + 
			"\n" + 
			"; (function() {\n" + 
			"    var open = window.XMLHttpRequest.prototype.open,\n" + 
			"    send = window.XMLHttpRequest.prototype.send,\n" + 
			"    onReadyStateChange;\n" + 
			"\n" + 
			"    function openReplacement(method, url, async) {\n" + 
			"        return open.apply(this, arguments);\n" + 
			"    }\n" + 
			"\n" + 
			"    function sendReplacement(data) {\n" + 
			"        if (this.onreadystatechange) this._onreadystatechange = this.onreadystatechange;\n" + 
			"        this.onreadystatechange = onReadyStateChangeReplacement;\n" + 
			"        if (window.hookIdName != undefined && window.hookIdValue != undefined){\n" + 
			"            this.setRequestHeader(window.hookIdName, window.hookIdValue);\n" + 
			"        }\n" + 
			"        return send.apply(this, arguments);\n" + 
			"    }\n" + 
			"\n" + 
			"    function onReadyStateChangeReplacement() {\n" + 
			"        if (this._onreadystatechange) return this._onreadystatechange.apply(this, arguments);\n" + 
			"    }\n" + 
			"\n" + 
			"    window.XMLHttpRequest.prototype.open = openReplacement;\n" + 
			"    window.XMLHttpRequest.prototype.send = sendReplacement;\n" + 
			"})();\n" + 
			"";
	
}
