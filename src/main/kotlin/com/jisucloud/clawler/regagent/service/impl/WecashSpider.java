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
public class WecashSpider implements PapaSpider {

    @Override
    public String message() {
        return "闪银奇异(www.wecash.net)官网,提供私人借款,大数据评估,信用评估,信用借款,信贷,信用卡借款,分期消费,互联网借款,在线借款,急速借款服务。";
    }

    @Override
    public String platform() {
        return "wecash";
    }

    private Map<String, String> getHeader(String account) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "m.wecash.net");
        headers.put("source", "adr");
        headers.put("version", "5.0.8");
        headers.put("sourcemark", "adr");
        headers.put("channelcode", "10014a");
        headers.put("brand", "vivo");
        headers.put("model", "vivo X20A");
        headers.put("imei", "867368032072541");
        headers.put("uniqueid", "538f667dff65a5c1");
        headers.put("access-token", "null");
        headers.put("refresh-token", "null");
        headers.put("package", "net.wecash.app");
        return headers;
    }

    private Map<String, String> getParams(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", mobile);
        params.put("password", "fccvbhgssqqq");
        params.put("sign", "072d36ed0aa071b2562936216837f755");
        return params;
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "https://m.wecash.net/platform/customer/login";
            Connection.Response response = Jsoup.connect(url).ignoreContentType(true).method(Connection.Method.POST)
                    .headers(getHeader(account))
                    .data(getParams(account))
                    .execute();

            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                System.out.println(result);
                if (result.getIntValue("errorCode") == 1005) {
                    return true;
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
        return "闪银";
    }
}
