package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class XicaiMaoSpider implements PapaSpider {


    @Override
    public String message() {
        return "喜财猫是中国领先的互联网科技平台,致力于安全高效的贷款服务。20万信贷经理24小时为您提供个人贷款、企业贷款、小额贷款、银行贷款、无抵押贷款.";
    }

    @Override
    public String platform() {
        return "xicaimao";
    }

    @Override
    public String home() {
        return "xicaimao.cn";
    }
    
    @Override
    public String platformName() {
        return "希财猫";
    }

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
    
//    public static void main(String[] args) {
//		System.out.println(new XicaiMaoSpider().checkTelephone("18210538513"));
//		System.out.println(new XicaiMaoSpider().checkTelephone("18210538577"));
//	}

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "okhttp/3.10.0");
        headers.put("Host", "passport.csaimall.com");
        headers.put("referer", "api.xicaimao.cn");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("version", "3.0.2");
        headers.put("versionCode", "32");
        headers.put("handleType", "Android");
        headers.put("phoneModel", "4.4.2");
        headers.put("equipType", "ANDROID");
        headers.put("cstk", "noLogin");
        return headers;
    }
    
    @Override
    public boolean checkTelephone(String account) {
        try {
            Session session = JJsoup.newSession();
            Connection.Response response = session.connect("https://passport.csaimall.com/app/user/login.do")
                    .headers(getHeader())
                    .data("mobileIden", "ffffffff-feb1-ed81-ffff-ffffc7c725a4")
                    .data("userName", account)
                    .data("password", "93B055F731F9ABC6BB4DCC17A8B7131B")
                    .data("mobileType", "8692-A00")
                    //.data("", "{\"password\":\"93B055F731F9ABC6BB4DCC17A8B7131B\",\"userName\":\""+account+"\",\"mobileType\":\"8692-A00\",\"mobileIden\":\"ffffffff-feb1-ed81-ffff-ffffc7c725a4\"}")
                    .method(Method.POST)
                    .ignoreContentType(true)
                    .execute();

            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                log.info("xicaimao:"+result);
                if (result.getString("resultMsg").equals("用户名或密码错误")) {
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

}
