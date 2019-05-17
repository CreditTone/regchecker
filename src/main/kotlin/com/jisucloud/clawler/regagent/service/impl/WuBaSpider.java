package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WuBaSpider implements PapaSpider {

    @Override
    public String message() {
        return "58同城分类信息网，为你提供房产、招聘、黄页、团购、交友、二手、宠物、车辆、周边游等海量分类信息，充分满足您免费查看/发布信息的需求。";
    }

    @Override
    public String platform() {
        return "58ganji";
    }

    @Override
    public String home() {
        return "58.com";
    }

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("uid", "");
        headers.put("psdk-d", "android");
        headers.put("58ua", "58app");
        headers.put("PPU", "");
        headers.put("deviceid", "086D41D57A6A0000");
        headers.put("psdk-v", "2.0.5.5");
        headers.put("58mac", "08:6D:41:D5:7A:6A");
        headers.put("sv", "2");
        headers.put("os", "android");
        headers.put("platform", "android");
        headers.put("osv", "4.4.2");
        headers.put("id58", "99368684100365");
        headers.put("imei", "864394010081091");
        headers.put("maptype", "2");
        headers.put("m", "08:6D:41:D5:7A:6A");
        headers.put("rimei", "864394010081091");
        headers.put("nop", "1878733712");
        headers.put("product", "58app");
        headers.put("uniqueid", "b40fdc05cc4f00cadd6e59019b101bba");
        headers.put("r", "800_480");
        headers.put("owner", "baidu");
        headers.put("productorid", "-1");
        headers.put("brand", "OPPO");
        headers.put("ua", "OPPO A59m");
        headers.put("http.protocol.cookie-policy", "compatibility");
        headers.put("uuid", "82dbd5ac-55bc-43df-919b-e94a2f65e86f");
        headers.put("Host", "passport.58.com");
        return headers;
    }

    private Map<String, String> getParams(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("rsakeyversion", "1");
        params.put("isremember", "false");
        params.put("username", mobile);
        params.put("source", "58app-android");
        params.put("validcodetype", "200");
        params.put("password", "2rfshpHVyEA2JZeZiYMjBoBos-FjwwD0PaNWhWZkSXjY3qpM1VdyK0TroL3j41nfYHGSH3XFDiJSNxB829xIJQU2vaOSFQ2XewGOqhRJs-De3rCXhkv8H7TW3S51_drmnCT2L26Eu510JFuYc2xw2bgeDSBcl79dxEm2aWwe05IXRNeF6KcdTQ4YRy8IKmKY37kwnXThaudt-mT9605Va-eVLcMK2EErB5aDQoJzNEupNPaQfSUvX-H8HflrpCa_Lh0U068ZYHKFcys4pXgZHpEEz88WYYcDhgHInybVClhArgVPn_RfbzmsB_fFj6gmFSOu8EgKY4SFF5Qov5Tqd-dLrMrNJgjnD28SiekKCSCCpr0hrn8zhxdwQPcn8ib-aqPGeXfr2V_mpwbu3s3pdDpamwROveqq1Md-J6YOBW1QHfDYAw9MYKyqurxFXd9U330BKEnEO9Z5QWpK6e9Y0qLXRxT3AjYML-ZdNrNMN-4_LKTEyy0FoHTnY6G6vlk4AQ==");
        params.put("vptype", "RSA2");
        return params;
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "https://passport.58.com/login/dologin";
            Connection.Response response = JJsoupUtil.newProxySession().connect(url)
                    .method(Connection.Method.POST)
                    .data(getParams(account))
                    .headers(getHeader())
                    .ignoreContentType(true)
                    .userAgent("Dalvik/1.6.0 (Linux; U; Android 4.4.2; OPPO A59m Build/KOT49H)")
                    .execute();
            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                System.out.println(account + " 58:" + result);
                if (result.getIntValue("code") == 772) {
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
        return checkTelephone(account);
    }

    @Override
    public Map<String, String> getFields() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String platformName() {
        return "58赶集";
    }

    @Override
    public Map<String, String[]> tags() {
        return null;
    }

}
