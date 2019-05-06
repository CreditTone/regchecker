package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ENet126EmailSpider implements PapaSpider {

    @Override
    public String message() {
        return "126邮箱是网易公司于2001年11月推出的免费的电子邮箱，是网易公司倾力打造的专业电子邮局，采用了创新Ajax技术，同等网络环境下，页面响应时间最高减少90%，垃圾邮件及病毒有效拦截率超过98%和99.8%。";
    }

    @Override
    public String platform() {
        return "126Email";
    }

    @Override
    public String platformName() {
        return "网易126邮箱";
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "http://www.emailcamel.com/api/single/validate/?usr=guozhong@quicklyun.com&pwd=qqadmin&email=" + account + "@126.com";
            Connection.Response response = Jsoup.connect(url).execute();
            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                System.out.println(result);
                if ("success".equals(result.getString("verify_status"))) {
                    if ("valid".equals(result.getString("verify_result"))) {
                        return true;
                    }
                    if ("catch-all".equals(result.getString("verify_result"))) {
                        return true;
                    }
                } else {
                    System.out.println("emailcamel效验失败，请充值");
                }
            }
        } catch (Exception e) {
            System.out.println("异常：" + e.getMessage());
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

}
