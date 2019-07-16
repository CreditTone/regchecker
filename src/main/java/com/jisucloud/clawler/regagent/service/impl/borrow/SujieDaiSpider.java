package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import org.jsoup.Connection;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UsePapaSpider
public class SujieDaiSpider extends PapaSpider {

    @Override
    public String message() {
        return "速借贷作为一款应用，解决的是需求用户贷款难的问题，我们用专业的服务，解决您贷款难、审核严、下款慢、利息高的问题！！！";
    }

    @Override
    public String platform() {
        return "sujiedai";
    }

    @Override
    public String home() {
        return "kyxdloan.com";
    }

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Sign", "EDCB7FAA0AC96CECA3652AE07E4503840FE04910");
        headers.put("Host", "api.kyxdloan.com");
        return headers;
    }

    private Map<String, String> getParams(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("u", mobile);
        params.put("isremember", "false");
        params.put("deviceType", "Android");
        params.put("deviceToken", "7121d2f6f3a817f98e74e62172733877");
        params.put("p", "24ffc00d04bc540d8cb473d5d37b22fa");
        params.put("version", "1.0.7");
        params.put("timestamp", System.currentTimeMillis() + "");
        return params;
    }
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268900", "18210538513");
	}

    @Override
    public boolean checkTelephone(String account) {
        try {
            String url = "https://api.kyxdloan.com/user/signin_check";
            Connection.Response response = JJsoupUtil.newProxySession().connect(url)
                    .headers(getHeader())
                    .data(getParams(account))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .userAgent("deviceToken:7121d2f6f3a817f98e74e62172733877, client:android, version:1.0.7, build:181207, channel:wandoujia, userId:, screenSize:480x800,safeToken:904ef22b584147d5650c1f79a64354aa,Fastloan:1.0.7,appName:fastloan")
                    .execute();
            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                if (result.getString("moreInfo").contains("密码错误")) {
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
        return checkTelephone(account);
    }

    @Override
    public Map<String, String> getFields() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String platformName() {
        return "速借贷";
    }

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}

}
