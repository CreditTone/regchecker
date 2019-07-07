package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@UsePapaSpider
public class DouDouCashMSpider extends PapaSpider {

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    @Override
    public String message() {
        return "豆豆钱是一款互联网智能信贷产品app。可为人们提供5000-50000元的贷款服务。纯线上操作，到账迅速。";
    }

    @Override
    public String platform() {
        return "doudoucash";
    }

    @Override
    public String home() {
        return "ddcash.cn";
    }
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268900", "18210538513");
	}

    private String getRequestBody(String mobile) {
        JSONObject q = new JSONObject();
        JSONObject reqParam = new JSONObject();
        reqParam.put("account", mobile);
        q.put("funcCode", "FUNC_IS_REGISTERED");
        q.put("reqParamStr", reqParam.toJSONString());
        q.put("version", "1.0.0");
        return q.toJSONString();
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            JSONObject appvinv = new JSONObject();
            appvinv.put("appVersionCode", "557");
            appvinv.put("appAgent", "DDAPP");
            appvinv.put("platform", "Android");
            appvinv.put("appVersionName", "5.5.7");
//			RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), getRequestBody(account));
            FormBody formBody = new FormBody.Builder()
                    .add(getRequestBody(account), "")
                    .build();
            Request request = new Request.Builder()
                    .url("https://core.ddcash.cn/gateway/common")
                    .post(formBody)
                    .addHeader("appAgent", "DDAPP")
                    .addHeader("app-version-info", appvinv.toJSONString())
                    .addHeader("Charsert", "UTF-8")
                    .addHeader("Accept-Encoding", "gzip,deflate")
                    .addHeader("Host", "core.ddcash.cn")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 5.1.1; oppo r9 plusm a Build/LMY49I)")
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            JSONObject result = JSON.parseObject(response.body().string());
            System.out.println("doudoucach:" + result);
            if (result.getString("data").equals("true")) {
                return true;
            }
        } catch (Exception e) {
//			e.printStackTrace();
            System.out.println("豆豆撞库异常:" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean checkEmail(String account) {
        return false;
    }

    @Override
    public Map<String, String> getFields() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String platformName() {
        return "豆豆钱包";
    }

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
}
