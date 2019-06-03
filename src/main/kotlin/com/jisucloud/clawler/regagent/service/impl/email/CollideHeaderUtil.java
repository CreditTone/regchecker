package com.jisucloud.clawler.regagent.service.impl.email;

import java.util.HashMap;
import java.util.Map;

public class CollideHeaderUtil {

    public static Map<String, String> wecashHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "m.wecash.net");
        headers.put("source", "adr");
        headers.put("version", "5.0.8");
        headers.put("sourcemark", "adr");
        headers.put("channelcode", "10014a");
        headers.put("brand", "vivo");
        headers.put("model", "vivo X20A");
        headers.put("imei", "867358032072541");
        headers.put("uniqueid", "82f57d0eb0987f96");
        headers.put("access-token", "null");
        headers.put("package", "net.wecash.app");
        headers.put("Content-Typp", "application/x-www-form-urlencoded; charset=utf-8");
        headers.put("refresh-token", "null");
        headers.put("Cookie", "aliyungf_tc=AQAAAG3jVG3cywYAHtbzcp2ZG8ph8gqV");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("User-Agent", "okhttp/3.6.0");
        return headers;
    }

    //post http://userinfo.user.kugou.com/v3/retrieve_pwd
    public static Map<String, String> kugouHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "userinfo.user.kugou.com");
        headers.put("Content-Typp", "text/plain; charset=UTF-8");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("User-Agent", "Android810-AndroidPhone-9156-238-0-User-wifi");
        return headers;
    }

    //post https://m-its.tax861.gov.cn/web/zh/login/zhmmdl
    public static Map<String, String> taxAppHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "m-its.tax861.gov.cn");
        headers.put("Origin", "file://");
        headers.put("Host", "m-its.tax861.gov.cn");
        headers.put("Authorization", "null");
        headers.put("X-Requested-With", "cn.gov.tax.its");
        headers.put("Cookie", "aliyungf_tc=AQAAAIz7dDwobQgAVweVPQyWs1adAFP7; acw_tc=7b39758215513306905653535e7a43ef6a0f48f3a0328b483bfc332539cc35; BIGipServerSJDLRK_POOL=1551460362.26946.0000");
        headers.put("Content-Typp", "text/plain; charset=UTF-8");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 8.1.0; vivo X20A Build/OPM1.171019.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36  ItsClient");
        return headers;
    }

    //post https://safecenter.mybank.cn/account/process.json
    public static Map<String, Object> aliBank() {
        Map<String, Object> headers = new HashMap<>();
        return headers;
    }
}
