package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import me.kagura.Session;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Web12306Spider implements PapaSpider {


    private Map<String, String> fields = null;

    @Override
    public String message() {
        return "中国铁路客户服务中心（12306网）是铁路服务客户的重要窗口，将集成全路客货运输信息，为社会和铁路客户提供客货运输业务和公共信息查询服务。客户通过登录本网站，可以查询旅客列车时刻表、票价、列车正晚点、车票余票、售票代售点、货物运价、车辆技术参数以及有关客货运规章。";
    }

    @Override
    public String platform() {
        return "12306";
    }

    @Override
    public String home() {
        return "12306.cn";
    }

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "kyfw.12306.cn");
        headers.put("Referer", "https://kyfw.12306.cn/otn/regist/init");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    private Map<String, String> getParams(String mobile, String email) {
        Map<String, String> params = new HashMap<>();
        params.put("loginUserDTO.user_name", "wx18910674321");
        params.put("userDTO.password", "qqadmin127");
        params.put("confirmPassWord", "qqadmin127");
        params.put("loginUserDTO.id_type_code", "1");
        params.put("idTypeRadio", "1");
        params.put("loginUserDTO.name", "孙良成");
        params.put("loginUserDTO.id_no", "320219196503306777");
        params.put("loginUserDTO.GAT_valid_date_end", "");
        params.put("userDTO.born_date", "");
        params.put("userDTO.country_code", "CN");
        params.put("userDTO.email", email);
        params.put("userDTO.mobile_no", mobile);
        params.put("passenger_type", "1");
        params.put("studentInfoDTO.province_code", "1");
        params.put("studentInfoDTO.school_code", "");
        params.put("studentInfoDTO.school_name", "简码/汉字");
        params.put("studentInfoDTO.department", "");
        params.put("studentInfoDTO.school_class", "");
        params.put("studentInfoDTO.student_no", "");
        params.put("studentInfoDTO.school_system", "1");
        params.put("studentInfoDTO.enter_year", "2019");
        params.put("studentInfoDTO.preference_card_no", "");
        params.put("studentInfoDTO.preference_from_station_name", "简码/汉字");
        params.put("studentInfoDTO.preference_from_station_code", "");
        params.put("studentInfoDTO.preference_to_station_name", "简码/汉字");
        params.put("studentInfoDTO.preference_to_station_code", "");
        return params;
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            Map<String, String> cookies = Jsoup.connect("https://www.12306.cn/index/").ignoreContentType(true).execute().cookies();
            Connection.Response response = Jsoup.connect("https://kyfw.12306.cn/otn/regist/init")
                    .cookies(cookies)
                    .ignoreContentType(true)
                    .execute();

            cookies.putAll(response.cookies());

            response = Jsoup.connect("https://kyfw.12306.cn/otn/regist/getRandCode")
                    .ignoreContentType(true)
                    .data(getParams(account, ""))
                    .headers(getHeader())
                    .method(Connection.Method.POST)
                    .execute();

            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                System.out.println(result);
                if (result.getBooleanValue("status") == true) {
                    String msg = result.getJSONObject("data").getString("msg");
                    System.out.println("12306:" + account + ":" + result);
                    if (msg != null && msg.contains("您输入的手机号码已被其他注册用户")) {
                        Matcher matcher = Pattern.compile("其他注册用户（([^）]+)）使用").matcher(msg);
                        if (matcher.find()) {
                            fields = new HashMap<>();
                            fields.put("name", matcher.group(1));
                        }
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
        try {
            Session session = JJsoupUtil.newProxySession();
            session.connect("https://www.12306.cn/index/").ignoreContentType(true).execute();
            session.connect("https://kyfw.12306.cn/otn/regist/init")
                    .ignoreContentType(true)
                    .execute();

            Connection.Response response = session.connect("https://kyfw.12306.cn/otn/regist/getRandCode")
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .data(getParams("18700006333", account))
                    .headers(getHeader())
                    .execute();
            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                if (result.getBooleanValue("status") == true) {
                    String msg = result.getJSONObject("data").getString("msg");
                    System.out.println(account + ":" + msg);
                    if (msg != null && msg.contains("account")) {
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
    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String platformName() {
        return "12306";
    }

    @Override
    public Map<String, String[]> tags() {
        return null;
    }

}
