package com.jisucloud.clawler.regagent.service.impl.life;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HuaWeiCloudSpider implements PapaSpider {


    @Override
    public String message() {
        return "华为云端安全存储和管理华为手机用户的照片、联系人、备忘录等重要数据，并同步更新到您的其他华为设备。远程定位和锁定设备，擦除数据。";
    }

    @Override
    public String platform() {
        return "huawei";
    }
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13925306966", "18210538513");
	}

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "hwid1.vmall.com");
        headers.put("Page-Token", "Mi3BjYqi4XJtHFUtjzF5S5grFhJs5CzG");
        headers.put("Cookie", "JSESSIONID=D792DA8145DED1A45DB4C9BE98A029122AEE23E26F4EE51C; sid=204937b636f7cf077c9ec41eb8345211852dac2610cbccb989bea79bd1867e3afb34f713b7b03887373b; Hm_lvt_ad7c111dd23c2ce53a6a3716f4dab0de=1551602286; VERSION_NO=UP_CAS_2.6.5.103");
        headers.put("Pragma", "no-cache");
        headers.put("Referer", "https://hwid1.vmall.com/AMW/portal/resetPwd/forgetbyid.html?reqClientType=1&loginChannel=1000002&countryCode=cn&loginUrl=https%3A%2F%2Fcloud.huawei.com%3A443%2Fothers%2Flogin.action&service=https%3A%2F%2Fcloud.huawei.com%3A443%2Fothers%2Flogin.action&lang=zh-cn&themeName=red");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    private Map<String, String> getParams(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        return params;
    }

    @Override
    public boolean checkTelephone(String account) {
        try {

            Map<String, String> cookies = Jsoup.connect("https://hwid1.vmall.com/AMW/portal/resetPwd/forgetbyid.html?reqClientType=1&loginChannel=1000002&countryCode=cn&loginUrl=https%3A%2F%2Fcloud.huawei.com%3A443%2Fothers%2Flogin.action&service=https%3A%2F%2Fcloud.huawei.com%3A443%2Fothers%2Flogin.action&lang=zh-cn&themeName=red").ignoreContentType(true).execute().cookies();

            String url = "http://reg.email.163.com/unireg/call.do?cmd=added.mobilemail.checkBinding";
            Connection.Response response = Jsoup.connect(url)
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .headers(getHeader())
                    .data(getParams(account))
                    .ignoreContentType(true)
                    .execute();
            System.err.println(response.body());
            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                System.out.println(result);
                if (result.getIntValue("code") == 201) {
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
        return false;
    }

    @Override
    public Map<String, String> getFields() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String platformName() {
        return "华为手机云";
    }

	@Override
	public String home() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] tags() {
		// TODO Auto-generated method stub
		return null;
	}
}
