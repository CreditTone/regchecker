package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MainMainSpider implements PapaSpider {

    @Override
    public String message() {
        return "脉脉(maimai.cn),中国领先的职场实名社交平台,利用科学算法为职场人拓展人脉,降低商务社交门槛,实现各行各业交流合作。";
    }

    @Override
    public String platform() {
        return "mainmain";
    }

    @Override
    public String home() {
        return "taou.com";
    }

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Maimai-Reqid", UUID.randomUUID().toString().replaceAll("\\-", ""));
        headers.put("Host", "open.taou.com");
        return headers;
    }

    private Map<String, String> getParams(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("info_type", "2");
        params.put("new_fr", "2");
        params.put("cnt", "0");
        params.put("token", "8012b6879f50fdb829397777272448d3");
        params.put("imei", "864394010081091");
        params.put("account", "+86-" + mobile);
        params.put("token_type", "100");
        params.put("stage", "complete_uinfo");
        params.put("password", "wxilovexiaoyu");
        params.put("dev_type", "3");
        return params;
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "https://open.taou.com/maimai/user/v3/login?account=+86-" + account + "&u=&access_token=&version=5.0.10&ver_code=android_10012&channel=Baidu&vc=" + URLEncoder.encode("Android 4.4.2/19") + "&push_permit=1&net=wifi&open=icon&appid=3&device=" + URLEncoder.encode("motorola XT1570") + "&imei=864394010081091&udid=" + UUID.randomUUID() + "&is_push_open=1&isEmulator=0&real_imei=864394010081091&imsi=460070810941213&android_id=086D41D57A6A0000&macmd5=82E0F0947FD76857166B7F5A1A6BEFA4&vender=motorola&ssid=" + URLEncoder.encode("CHINA MOBILE") + "&bssid=00:FF:C3:08:2C:BB&install_uuid=0e041917-3bb2-421f-8ddd-18d7bd9c7075&store=baidu&appsflyer_id=1552545202067-4552248765736380010&language=zh_CN&preinstall=0&package_name=com.taou.maimai&af_status=Organic&density=2.0&launch_uuid=3ad3b82c-3f25-47d2-b9f2-4a0c9fd936e8&session_uuid=f85bded4-f8dd-4e5f-a35c-fa2f856da218&from_page=taoumaimai://page?name=com.taou.maimai.growth.component.MobileRegisterLoginFragment&uuid=6d05cbcd-e5e1-4d9d-9f6a-4d720e7b21df&src_page=taoumaimai://page?name=com.taou.maimai.growth.component.MobileRegisterLoginActivity&uuid=da30d36a-e5c3-40b5-a9d6-f8a33d27b42d&to_page=taoumaimai://page?name=com.taou.maimai.growth.component.MobileRegisterLoginFragment&uuid=6d05cbcd-e5e1-4d9d-9f6a-4d720e7b21df&flowType=10&need_script=1";

            Connection.Response response = JJsoupUtil.newProxySession().connect(url)
                    .headers(getHeader())
                    .data(getParams(account))
                    .userAgent("{motorola XT1570} [Android 4.4.2/19]/MaiMai 5.0.10(10012)")
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();

            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                System.out.println(result);
                if (result.getIntValue("error_code") == 21009) {
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
        return "脉脉";
    }

    @Override
    public Map<String, String[]> tags() {
        return null;
    }
}
