package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import me.kagura.Session;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class jiQianBaoSpider implements PapaSpider {


    @Override
    public String message() {
        return "急钱宝app是一款贷款产品十分丰富的综合贷款平台。急钱宝app为大家精心准备了各种类型的贷款口子，每款产品都经过急钱宝平台审核，不管需要什么样的贷款产品都能满足你！";
    }

    @Override
    public String platform() {
        return "jiqianbao";
    }

    @Override
    public String home() {
        return "gzhaitui.cn";
    }
    
    @Override
    public String platformName() {
        return "急钱宝";
    }

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "web.gzhaitui.cn");
        headers.put("devices", "android");
        headers.put("actoken", "faa0174b62e49d085d735a9a81d06325");
        headers.put("User-Agent", "okhttp/2.5.0");
        return headers;
    }
    
    private Map<String, String> getParams(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("account", mobile);
        params.put("password", "fccvbhgssqqq");
        params.put("channel", "9");
        return params;
    }

    public static void main(String[] args) {
		System.out.println(new jiQianBaoSpider().checkTelephone("18210538513"));
		System.out.println(new jiQianBaoSpider().checkTelephone("18210531111"));
	}

    @Override
    public boolean checkTelephone(String account) {
        try {
            Session session = JJsoupUtil.newProxySession();
            Connection.Response response = session.connect("https://web.gzhaitui.cn/api/user2/login")
                    .headers(getHeader())
                    .data(getParams(account))
                    .method(Method.POST)
                    .ignoreContentType(true)
                    .execute();

            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                System.out.println(result);
                if (result.getString("msg").contains("密码错误")) {
                    return true;
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("Read timed out")) {
                return false;
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkEmail(String account) {
        return checkTelephone(account);
    }

    @Override
    public Map<String, String> getFields() {
        return null;
    }

    @Override
    public Map<String, String[]> tags() {
        return new HashMap<String, String[]>() {{
            put("金融理财", new String[]{"借贷", "贷超"});
        }};
    }
}
