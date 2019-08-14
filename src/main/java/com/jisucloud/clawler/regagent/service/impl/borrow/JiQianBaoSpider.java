package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.HashMap;
import java.util.Map;


@PapaSpiderConfig(
		home = "gzhaitui.cn", 
		message = "急钱宝app是一款贷款产品十分丰富的综合贷款平台。急钱宝app为大家精心准备了各种类型的贷款口子，每款产品都经过急钱宝平台审核，不管需要什么样的贷款产品都能满足你！", 
		platform = "jiqianbao", 
		platformName = "急钱宝", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "15985268900", "18210538513" })
public class JiQianBaoSpider extends PapaSpider {


    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "web.gzhaitui.cn");
        headers.put("devices", "android");
        headers.put("actoken", "faa0174b62e49d085d735a9a81d06325");
        headers.put("User-Agent", "okhttp/2.5.0");
        return headers;
    }
    
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

    
    public boolean checkEmail(String account) {
        return checkTelephone(account);
    }

    
    public Map<String, String> getFields() {
        return null;
    }

	
	
}
