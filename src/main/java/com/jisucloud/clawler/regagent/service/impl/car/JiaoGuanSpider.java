package com.jisucloud.clawler.regagent.service.impl.car;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import org.jsoup.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UsePapaSpider(exclude = true)
public class JiaoGuanSpider extends PapaSpider {

    @Override
    public String message() {
        return "交管12123官网提供全国车辆违章查询和罚款缴纳,驾照预约考试,您附近的车管所电话、地址、营业时间,驾驶证和行驶证业务办理,交管12123APP常见问题。";
    }

    @Override
    public String platform() {
        return "12123";
    }

    @Override
    public String home() {
        return "12123.com";
    }

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "www.12123.com");
        headers.put("Referer", "http://www.12123.com/forget.html");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210530000", "18210538513");
	}

    @Override
    public boolean checkTelephone(String account) {
        return false;
    }

    @Override
    public boolean checkEmail(String account) {
        return false;
    }

    @Override
    public Map<String, String> getFields() {
        return null;
    }

    @Override
    public String platformName() {
        return "交管12123";
    }

    @Override
	public String[] tags() {
		return new String[] {"违章查询"};
	}
}
