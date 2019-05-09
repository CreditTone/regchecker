package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import com.jisucloud.clawler.regagent.util.PingyinUtil;
import com.jisucloud.clawler.regagent.util.StringUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

@Component
public class DingDingSpider implements PapaSpider {

    private String name = "郭钟";

    @Override
    public String message() {
        return "钉钉(DingTalk)是中国领先的智能移动办公平台,由阿里巴巴集团开发,免费提供给所有中国企业,用于商务沟通和工作协同。";
    }

    @Override
    public String platform() {
        return "dinding";
    }

    @Override
    public String home() {
        return "dingtalk.com";
    }

    @Override
    public String platformName() {
        return "钉钉";
    }

    @Override
    public boolean checkTelephone(String account) {
        if (name != null && StringUtil.hasChinese(name)) {
            String pingyin = PingyinUtil.toPinyin(name);
            String email = pingyin + account.substring(account.length() - 4) + "@dingtalk.com";
            try {
                String url = "http://www.emailcamel.com/api/single/validate/?usr=guozhong@quicklyun.com&pwd=qqadmin&email=" + email;
                Connection.Response response = JJsoupUtil.newProxySession().connect(url).execute();
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
