package com.jisucloud.clawler.regagent.service.impl.car;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import org.jsoup.Connection;

import java.util.HashMap;
import java.util.Map;


@PapaSpiderConfig(
		home = "12123.com", 
		message = "交管12123官网提供全国车辆违章查询和罚款缴纳,驾照预约考试,您附近的车管所电话、地址、营业时间,驾驶证和行驶证业务办理,交管12123APP常见问题。", 
		platform = "12123", 
		platformName = "交管12123", 
		tags = { "违章查询" }, 
		testTelephones = { "18210530000", "18210538513" },
		exclude = true)
public class JiaoGuanSpider extends PapaSpider {

    

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "www.12123.com");
        headers.put("Referer", "http://www.12123.com/forget.html");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }
    
	
	

    
    public boolean checkTelephone(String account) {
        return false;
    }

    
    public boolean checkEmail(String account) {
        return false;
    }

    
    public Map<String, String> getFields() {
        return null;
    }

    
    

    
	
}
