package com.jisucloud.clawler.regagent.service.impl.email;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Enet163EmailSpider implements PapaSpider {

    @Override
    public String message() {
        return "网易公司（NASDAQ: NTES）是中国的互联网公司，利用互联网技术，加强人与人之间信息的交流和共享，实现“网聚人的力量”。创始人兼CEO是丁磊。";
    }

    @Override
    public String platform() {
        return "enet";
    }

    @Override
    public String home() {
        return "mail.163.com";
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "http://www.emailcamel.com/api/single/validate/?usr=guozhong@quicklyun.com&pwd=qqadmin&email=" + account + "@163.com";

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
        return "网易163邮箱";
    }

    @Override
  	public String[] tags() {
  		return new String[] {"邮箱"};
  	}
}
