package com.jisucloud.deepsearch.selenium;

public class AjaxListererJs {
	
	public static final String ArrayQueueJS = "function ArrayQueue() {\n" + 
			"    var arr = [];\n" + 
			"    //入队操作  \n" + 
			"    this.push = function(element) {\n" + 
			"        arr.push(element);\n" + 
			"        return true;\n" + 
			"    }\n" + 
			"    //出队操作  \n" + 
			"    this.pop = function() {\n" + 
			"        return arr.shift();\n" + 
			"    }\n" + 
			"    //获取队首  \n" + 
			"    this.getFront = function() {\n" + 
			"        return arr[0];\n" + 
			"    }\n" + 
			"    //获取队尾  \n" + 
			"    this.getRear = function() {\n" + 
			"        return arr[arr.length - 1]\n" + 
			"    }\n" + 
			"    //清空队列  \n" + 
			"    this.clear = function() {\n" + 
			"        arr = [];\n" + 
			"    }\n" + 
			"    //获取队长  \n" + 
			"    this.size = function() {\n" + 
			"        return length;\n" + 
			"    }\n" + 
			"};\n" + 
			"\n" + 
			"window.myQueue = new ArrayQueue();\n"
			+ "window.blockAjax = new Array();";
	
	public static final String AjaxListeneJS = "function needBlock(url){\n" + 
			"    for(var i = 0; i < window.blockAjax.length; i++) {\n" + 
			"              if(url.indexOf(window.blockAjax[i]) != -1) {\n" + 
			"                    return true;\n" + 
			"              }\n" + 
			"       }\n" + 
			"       return false;\n" + 
			"};\n" + 
			"\n" + 
			"; (function() {\n" + 
			"    var open = window.XMLHttpRequest.prototype.open,\n" + 
			"    send = window.XMLHttpRequest.prototype.send,\n" + 
			"    onReadyStateChange;\n" + 
			"\n" + 
			"    function openReplacement(method, url, async, user, password) {\n" + 
			"        // some code\n" + 
			"        if (needBlock(url)){\n" + 
			"            return \"error\";\n" + 
			"        }\n" + 
			"        this.requestUrl = url;\n" + 
			"        this.requestMethod = method;\n" + 
			"        if (method.toLowerCase() == 'get' && url.indexOf(\"?\") != -1) {\n" + 
			"            data = url.split('?');\n" + 
			"            this.requestData = data[1];\n" + 
			"        } else {\n" + 
			"            this.requestData = {}\n" + 
			"        }\n" + 
			"        return open.apply(this, arguments);\n" + 
			"    }\n" + 
			"\n" + 
			"    function sendReplacement(data) {\n" + 
			"        // some code\n" + 
			"        if (this.onreadystatechange) this._onreadystatechange = this.onreadystatechange;\n" + 
			"        this.onreadystatechange = onReadyStateChangeReplacement;\n" + 
			"        if (this.requestMethod.toLowerCase() == 'post') {\n" + 
			"            this.requestData = data;\n" + 
			"        }\n" + 
			"        return send.apply(this, arguments);\n" + 
			"    }\n" + 
			"\n" + 
			"    function onReadyStateChangeReplacement() {\n" + 
			"        // some code\n" + 
			"        if (this._onreadystatechange) return this._onreadystatechange.apply(this, arguments);\n" + 
			"    }\n" + 
			"\n" + 
			"    window.XMLHttpRequest.prototype.open = openReplacement;\n" + 
			"    window.XMLHttpRequest.prototype.send = sendReplacement;\n" + 
			"})();\n" + 
			"\n" + 
			"; (function() {\n" + 
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
			"    window.XMLHttpRequest = newXHR;\n" + 
			"})();\n" + 
			"\n" + 
			"window.addEventListener('ajaxLoad',\n" + 
			"function(e) {\n" + 
			"    var xhr = e.detail;\n" + 
			"    var cType = xhr.getResponseHeader('Content-Type')\n" + 
			"    console.log('cType:'+cType)\n" + 
			"    if (cType.indexOf('application') != -1 || cType.indexOf('charset') != -1) {\n" + 
			"        var result = new Object();\n" + 
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
