package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JiaoGuanSpider implements PapaSpider {

    @Override
    public String message() {
        return "交管12123官网提供全国车辆违章查询和罚款缴纳,驾照预约考试,您附近的车管所电话、地址、营业时间,驾驶证和行驶证业务办理,交管12123APP常见问题。";
    }

    @Override
    public String platform() {
        return "12123";
    }

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "www.12123.com");
        headers.put("Referer", "http://www.12123.com/forget.html");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            Map<String, String> cookies = Jsoup.connect("https://www.12123.com/forget.html")
                    .ignoreContentType(true)
                    .execute().cookies();
            Connection.Response execute = Jsoup.connect("https://www.12123.com/api/checkRegistered.json")
                    .cookies(cookies)
                    .headers(getHeader())
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("Host", "www.12123.com")
                    .header("Origin", "https://www.12123.com")
                    .header("Referer", "https://www.12123.com/forget.html")
                    .ignoreContentType(true)
                    .data("mobile", account)
                    .method(Connection.Method.POST)
                    .execute();

            if (execute != null) {
                JSON result = (JSON) JSON.parse(execute.body());
                System.out.println("12123www:" + result);
                if (result instanceof JSONObject) {
                    JSONObject obj = (JSONObject) result;
                    if (obj.getBooleanValue("isExist")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
