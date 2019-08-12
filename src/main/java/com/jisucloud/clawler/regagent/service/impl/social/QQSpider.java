package com.jisucloud.clawler.regagent.service.impl.social;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@UsePapaSpider(ignoreTestResult = true)
public class QQSpider extends PapaSpider {
	
	private OkHttpClient okHttpClient2 = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(1, TimeUnit.SECONDS).retryOnConnectionFailure(false).build();
	
    @Override
    public String message() {
        return "腾讯网从2003年创立至今,已经成为集新闻信息,区域垂直生活服务、社会化媒体资讯和产品为一体的互联网媒体平台。";
    }

    @Override
    public String platform() {
        return "qq";
    }

    @Override
    public String home() {
        return "qq.com";
    }
    
    @Override
    public String platformName() {
        return "腾讯QQ";
    }

    @Override
	public String[] tags() {
		return new String[] {"交友" , "社交"};
	}
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18810038000", "18210538513");
	}


    @Override
    public boolean checkTelephone(String account) {
        try {
        	Request request = new Request.Builder().url("https://aq.qq.com/cn2/reset_pwd/pc/pc_reset_pwd_get_uin_by_input_ajax?aq_account=" + account + "&qq_txwb_user_choice=0&_=" + System.currentTimeMillis())
					.addHeader("Host", "aq.qq.com")
					.addHeader("Referer", "https://aq.qq.com/v2/uv_aq/html/reset_pwd/pc_reset_pwd_input_account.html?v=")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("TE", "Trailers")
					.build();
        	String body = okHttpClient2.newCall(request).execute().body().string();;
            JSONObject result = JSON.parseObject(body);
            if (result.getString("ret").equals("0")) {
                return true;
            }
        } catch (Exception e) {
        	 //e.printStackTrace();
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
