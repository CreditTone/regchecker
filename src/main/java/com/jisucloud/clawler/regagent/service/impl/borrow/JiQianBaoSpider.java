package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UsePapaSpider
public class JiQianBaoSpider extends PapaSpider {


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
    
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268900", "18210538513");
	}

    @Override
    public boolean checkTelephone(String account) {
        try {
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .add("password", "fccvbhgssqqq")
	                .add("channel", "9")
	                .build();
			Request request = new Request.Builder().url("https://web.gzhaitui.cn/api/user2/login")
					.headers(Headers.of(getHeader()))
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("msg").contains("密码错误")) {
                return true;
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
        return null;
    }

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
}
