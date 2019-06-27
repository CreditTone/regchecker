package com.jisucloud.clawler.regagent.service.impl.email;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@UsePapaSpider
public class CDMA189EmailSpider implements PapaSpider {

    private final Logger log = LoggerFactory.getLogger(CDMA189EmailSpider.class);

    @Override
    public String message() {
        return "189免费邮箱是面向所有互联网用户的新型工作和商务邮箱,覆盖Web、Wap、 Pad、iOS、Android等多个终端,满足用户随时随地处理邮件需要。";
    }

    @Override
    public String platform() {
        return "189Email";
    }

    @Override
    public String home() {
        return "mail.189.cn";
    }

    @Override
    public String platformName() {
        return "电信189邮箱";
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "http://www.emailcamel.com/api/single/validate/?usr=guozhong@quicklyun.com&pwd=qqadmin&email=" + account + "@189.cn";
            Connection.Response response = JJsoupUtil.newProxySession().connect(url).ignoreContentType(true).execute();
            System.err.println(response.body());
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
                    log.error("emailcamel效验失败，请充值");
                }
            }
        } catch (Exception e) {
            log.error("checkTelephone异常----------", e);
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
	public String[] tags() {
		return new String[] {"邮箱"};
	}


}
