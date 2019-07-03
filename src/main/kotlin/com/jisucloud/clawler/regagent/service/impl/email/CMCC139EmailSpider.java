package com.jisucloud.clawler.regagent.service.impl.email;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;

import org.jsoup.Connection;

import java.util.Map;
import java.util.Set;

//@UsePapaSpider
@Slf4j
public class CMCC139EmailSpider implements PapaSpider {

    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("12010000000", "18210538513");
	}


    @Override
    public String message() {
        return "139邮箱是中国移动提供的电子邮件业务,以手机号@139.com作为邮箱地址,来邮短信及时提醒; 同时提供WEB、WAP、短彩信、APP等多种方式,随时随地收发邮件。";
    }

    @Override
    public String platform() {
        return "139Email";
    }

    @Override
    public String home() {
        return "mail.10086.cn";
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "http://www.emailcamel.com/api/single/validate/?usr=guozhong@quicklyun.com&pwd=qqadmin127&email=" + account + "@139.com";
            Connection.Response response = JJsoup.newSession().connect(url).timeout(1000 * 30).ignoreContentType(true).execute();
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
    public String platformName() {
        return "移动139邮箱";
    }

    @Override
  	public String[] tags() {
  		return new String[] {"邮箱"};
  	}
}
