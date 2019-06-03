package com.jisucloud.clawler.regagent.service.impl.life;
//package com.jisucloud.clawler.regagent.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.jisucloud.clawler.regagent.service.PapaSpider;
//import com.jisucloud.clawler.regagent.util.JJsoupUtil;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class KugouSpider implements PapaSpider {
//
//
//    @Override
//    public String message() {
//        return "酷狗音乐旗下最新最全的在线正版音乐网站,本站为您免费提供最全的在线音乐试听下载,以及全球海量电台和MV播放服务、最新音乐播放器下载。酷狗音乐 和音乐在一起。";
//    }
//
//    @Override
//    public String platform() {
//        return "kugou";
//    }
//
//    private Map<String, String> getHeader() {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Host", "userinfo.user.kugou.com");
//        return headers;
//    }
//
//    private String getRequestBody(String mobile) {
//        JSONObject q = new JSONObject();
//        q.put("p", "BC47FA92770C63BEA876E7D4B469B2344DBCE74CCCFFF13B088668E2C3CF00E4F60092390E3D22B12214A140D31F125E1E57AB65DA3A7EF290F8FFE1D779ADB4DE952C263AF07FF73347951616491C0580D7FF282C5A07C85E41E8D44D91E247F268A68C5A62D603EF2090F70B296EB2816D3E5A42121077B4159341B3E6DFE3");
//        q.put("verifycode", "");
//        q.put("appid", "1005");
//        q.put("mid", "333207709897494590093873904526503356942");
//        q.put("clientver", 9156);
//        q.put("clienttime", 1552622443);
//        q.put("type", 1);
//        q.put("uuid", "9aca770c299fcc8646d1c1ac3f50baaf");
//        q.put("verifykey", "");
//        q.put("key", "4344a35da73c0a04db24bf3829267ad3");
//        q.put("username", mobile);
//        return q.toJSONString();
//    }
//
//    @Override
//    public boolean checkTelephone(String account) {
//        try {
//            String url = "http://userinfo.user.kugou.com/v3/retrieve_pwd";
//            Connection.Response response = JJsoupUtil.newProxySession().connect(url)
//                    .headers(getHeader())
//                    .requestBody(getRequestBody(account))
//                    .userAgent("Android810-AndroidPhone-9156-238-0-User-wifi")
//                    .method(Connection.Method.POST)
//                    .ignoreContentType(true)
//                    .execute();
//
//            if (response != null) {
//                JSONObject result = JSON.parseObject(response.body());
//                System.out.println(account + " kugou:" + result);
//                if (result.getIntValue("status") == 1) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean checkEmail(String account) {
//        return checkTelephone(account);
//    }
//
//    @Override
//    public Map<String, String> getFields() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public String platformName() {
//        return "酷狗";
//    }
//
//}
