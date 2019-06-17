package com.jisucloud.deepsearch.selenium;

public class AjaxListererJs {
	
	public static void main(String[] args) {
		System.out.println(AjaxListeneJS);
	}
	
	public static final String ArrayQueueJS = "function ArrayQueue() {\n" + 
			"    var arr = [];\n" + 
			"    this.push = function(element) {\n" + 
			"        arr.push(element);\n" + 
			"        return true;\n" + 
			"    }\n" + 
			"    this.pop = function() {\n" + 
			"        return arr.shift();\n" + 
			"    }\n" + 
			"    this.getFront = function() {\n" + 
			"        return arr[0];\n" + 
			"    }\n" + 
			"    this.getRear = function() {\n" + 
			"        return arr[arr.length - 1]\n" + 
			"    }\n" + 
			"    this.clear = function() {\n" + 
			"        arr = [];\n" + 
			"    }\n" + 
			"    this.size = function() {\n" + 
			"        return length;\n" + 
			"    }\n" + 
			"};\n" + 
			"\n" + 
			"window.injectedListener = new Object();\n" + 
			"window.myQueue = new ArrayQueue();\n" + 
			"window.blockAjax = new Array();\n" + 
			"window.fixPostData = new Object();\n" + 
			"window.fixGetData = new Object();\n";
	
	public static final String OprationVarJS = "window.needListening = function needListening(url) {\n" + 
			"    if (url.length == 0) {\n" + 
			"        return false\n" + 
			"    }\n" + 
			"    for (var key in window.injectedListener) {\n" + 
			"        var matchUrl = window.injectedListener[key];\n" + 
			"        if (url.indexOf(matchUrl) != -1 || matchUrl.indexOf(url) != -1) {\n" + 
			"            return true;\n" + 
			"        }\n" + 
			"    }\n" + 
			"    return false;\n" + 
			"};\n" + 
			"\n" + 
			"window.needBlock = function needBlock(url) {\n" + 
			"    if (url.length == 0) {\n" + 
			"        return false\n" + 
			"    }\n" + 
			"    for (var i = 0; i < window.blockAjax.length; i++) {\n" + 
			"        if (url.indexOf(window.blockAjax[i]) != -1 || window.blockAjax[i].indexOf(url) != -1) {\n" + 
			"            return true;\n" + 
			"        }\n" + 
			"    }\n" + 
			"    return false;\n" + 
			"};\n" + 
			"\n" + 
			"window.needFixPost = function needFixPost(url) {\n" + 
			"    if (url.length == 0) {\n" + 
			"        return false\n" + 
			"    }\n" + 
			"    for (var key in window.fixPostData) {\n" + 
			"        if (url.indexOf(key) != -1 || key.indexOf(url) != -1) {\n" + 
			"            return true;\n" + 
			"        }\n" + 
			"    }\n" + 
			"    return false;\n" + 
			"};\n" + 
			"\n" + 
			"window.needFixGet = function needFixGet(url) {\n" + 
			"    if (url.length == 0) {\n" + 
			"        return false\n" + 
			"    }\n" + 
			"    for (var key in window.fixGetData) {\n" + 
			"        if (url.indexOf(key) != -1 || key.indexOf(url) != -1) {\n" + 
			"            return true;\n" + 
			"        }\n" + 
			"    }\n" + 
			"    return false;\n" + 
			"};\n" + 
			"\n" + 
			"window.getFixGetData = function getFixGetData(url) {\n" + 
			"    if (url.length == 0) {\n" + 
			"        return \"\"\n" + 
			"    }\n" + 
			"    for (var key in window.fixGetData) {\n" + 
			"        if (url.indexOf(key) != -1 || key.indexOf(url)) {\n" + 
			"            return window.fixGetData[key];\n" + 
			"        }\n" + 
			"    }\n" + 
			"    return \"\";\n" + 
			"};\n" + 
			"\n" + 
			"window.getFixPostData = function getFixPostData(url) {\n" + 
			"    if (url.length == 0) {\n" + 
			"        return \"\"\n" + 
			"    }\n" + 
			"    for (var key in window.fixPostData) {\n" + 
			"        if (url.indexOf(key) != -1 || key.indexOf(url)) {\n" + 
			"            return window.fixPostData[key];\n" + 
			"        }\n" + 
			"    }\n" + 
			"    return \"\";\n" + 
			"};";
	
	public static final String ReplaceXHRJS = "; (function() {\n" + 
			"    var open = window.XMLHttpRequest.prototype.open,\n" + 
			"    send = window.XMLHttpRequest.prototype.send,\n" + 
			"    onReadyStateChange;\n" + 
			"\n" + 
			"    function openReplacement(method, url, async) {\n" + 
			"        if (needBlock(url)) {\n" + 
			"            return \"error\";\n" + 
			"        }\n" + 
			"        this.requestUrl = url;\n" + 
			"        this.requestMethod = method;\n" + 
			"        if (method.toLowerCase() == 'get' && url.indexOf(\"?\") != -1) {\n" + 
			"            var pathAndParams = url.split('?');\n" + 
			"            if (needFixGet(url)) {\n" + 
			"                pathAndParams[1] = getFixGetData(url);\n" + 
			"                url = pathAndParams[0] + '?' + pathAndParams[1];\n" + 
			"                arguments['1'] = url;\n" + 
			"            }\n" + 
			"            this.requestData = pathAndParams[1];\n" + 
			"        } else {\n" + 
			"            this.requestData = {}\n" + 
			"        }\n" + 
			"        return open.apply(this, arguments);\n" + 
			"    }\n" + 
			"\n" + 
			"    function sendReplacement(data) {\n" + 
			"        if (this.onreadystatechange) this._onreadystatechange = this.onreadystatechange;\n" + 
			"        this.onreadystatechange = onReadyStateChangeReplacement;\n" + 
			"        if (this.requestMethod.toLowerCase() == 'post') {\n" + 
			"            if (needFixPost(this.requestUrl)) {\n" + 
			"                var fixPostData = getFixPostData(this.requestUrl);\n" + 
			"                arguments['0'] = fixPostData;\n" + 
			"                this.requestData = fixPostData;\n" + 
			"            } else {\n" + 
			"                this.requestData = data;\n" + 
			"            }\n" + 
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
			"})();";
	
	public static final String AjaxListeneJS = "; (function() {\n" + 
			"    function ajaxEventTrigger(event) {\n" + 
			"        var ajaxEvent = new CustomEvent(event, {\n" + 
			"            detail: this\n" + 
			"        });\n" + 
			"        window.dispatchEvent(ajaxEvent);\n" + 
			"    }\n" + 
			"\n" + 
			"    var oldXHR = window.XMLHttpRequest;\n" + 
			"\n" + 
			"    function newXHR() {\n" + 
			"        var realXHR = new oldXHR();\n" + 
			"\n" + 
			"        realXHR.addEventListener('abort',\n" + 
			"        function() {\n" + 
			"            ajaxEventTrigger.call(this, 'ajaxAbort');\n" + 
			"        },\n" + 
			"        false);\n" + 
			"\n" + 
			"        realXHR.addEventListener('error',\n" + 
			"        function() {\n" + 
			"            ajaxEventTrigger.call(this, 'ajaxError');\n" + 
			"        },\n" + 
			"        false);\n" + 
			"\n" + 
			"        realXHR.addEventListener('load',\n" + 
			"        function() {\n" + 
			"            ajaxEventTrigger.call(this, 'ajaxLoad');\n" + 
			"        },\n" + 
			"        false);\n" + 
			"\n" + 
			"        realXHR.addEventListener('loadstart',\n" + 
			"        function() {\n" + 
			"            ajaxEventTrigger.call(this, 'ajaxLoadStart');\n" + 
			"        },\n" + 
			"        false);\n" + 
			"\n" + 
			"        realXHR.addEventListener('progress',\n" + 
			"        function() {\n" + 
			"            ajaxEventTrigger.call(this, 'ajaxProgress');\n" + 
			"        },\n" + 
			"        false);\n" + 
			"\n" + 
			"        realXHR.addEventListener('timeout',\n" + 
			"        function() {\n" + 
			"            ajaxEventTrigger.call(this, 'ajaxTimeout');\n" + 
			"        },\n" + 
			"        false);\n" + 
			"\n" + 
			"        realXHR.addEventListener('loadend',\n" + 
			"        function() {\n" + 
			"            ajaxEventTrigger.call(this, 'ajaxLoadEnd');\n" + 
			"        },\n" + 
			"        false);\n" + 
			"\n" + 
			"        realXHR.addEventListener('readystatechange',\n" + 
			"        function() {\n" + 
			"            ajaxEventTrigger.call(this, 'ajaxReadyStateChange');\n" + 
			"        },\n" + 
			"        false);\n" + 
			"\n" + 
			"        return realXHR;\n" + 
			"    }\n" + 
			"\n" + 
			"window.XMLHttpRequest = newXHR;\n" + 
			"})();";
	
	public static final String AjaxPushResultJS = "window.addEventListener('ajaxLoad',\n" + 
			"function(e) {\n" + 
			"    var xhr = e.detail;\n" + 
			"    if (needListening(xhr.requestUrl)){\n" + 
			"        var cType = xhr.getResponseHeader('Content-Type')\n" + 
			"        var result = new Object();\n" + 
			"        result.ContentType = cType;\n" + 
			"        result.requestUrl = xhr.requestUrl;\n" + 
			"        result.requestMethod = xhr.requestMethod;\n" + 
			"        result.requestData = xhr.requestData;\n" + 
			"        result.responseText = xhr.responseText;\n" + 
			"        var resultJson = JSON.stringify(result);\n" + 
			"        window.myQueue.push(resultJson);\n" + 
			"    }\n" + 
			"});";
	
	public static final String ajaxGetJs = 
			"function getAjaxData(){\n" + 
			"    if (window.myQueue != undefined) {\n" + 
			"        var ret = window.myQueue.pop();\n" + 
			"        if (ret != undefined){\n" + 
			"            return ret;\n" + 
			"        }\n" + 
			"    }\n" + 
			"    return \"\";\n" + 
			"};\n" + 
			"return getAjaxData();";
}
