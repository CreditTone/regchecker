package com.jisucloud.clawler.regagent.service.impl.email;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SohuEmailSpider implements PapaSpider {

    @Override
    public String message() {
        return "搜狐（ sohu）是中国四大门户网站之一，公司旗下推出的电子邮箱服务是目前国内最大的邮箱服务商之一。";
    }

    @Override
    public String platform() {
        return "sohuEmail";
    }

    @Override
    public String home() {
        return "mail.sohu.com";
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "http://www.emailcamel.com/api/single/validate/?usr=guozhong@quicklyun.com&pwd=qqadmin&email=" + account + "@sohu.com";
            Connection.Response response = JJsoupUtil.newProxySession().connect(url)
                    .ignoreContentType(true)
                    .execute();
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

    @Override
    public String platformName() {
        return "搜狐邮箱";
    }


    @Override
    public Map<String, String[]> tags() {
        return null;
    }
}
