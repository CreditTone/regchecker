package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;

import me.kagura.JJsoup;
import me.kagura.Session;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class JuziFenqiSpider implements PapaSpider {

    @Override
    public String message() {
        return "桔子分期是一家专注于年轻人分期购物的金融服务平台,是国内领先的年轻人分期消费服务商。";
    }

    @Override
    public String platform() {
        return "juzifenqi";
    }

    @Override
    public String home() {
        return "juzifenqi.com";
    }
    
    @Override
    public String platformName() {
        return "桔子分期";
    }

    @Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("金融理财", new String[] { "借贷", "消费分期" });
			}
		};
	}

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "juzifenqi.com");
        headers.put("channel", "1");
        headers.put("source", "1");
        headers.put("versionName", "1");
        headers.put("versionCode", "124");
        headers.put("imei", "352284040670808");
        headers.put("token", "");
        headers.put("User-Agent", "okhttp/3.11.0");
        return headers;
    }
    
	private Map<String, String> getParams(String mobile) {
		Random random = new Random();
		Map<String, String> params = new HashMap<>();
		params.put("mobile", mobile);
		params.put("password", "dasdasd12");
		params.put("equipmentName", "QiKU 8692-A00");
		params.put("loginCode", "352284040670808");
		params.put("mobileBrand", "8692-A00");
		params.put("apiToken", "5c8cf4f9d0b76095196c1691c9d356a4");
		params.put("time",  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		params.put("accurate", "广西壮族自治区柳州市城中区环江滨水大道168号靠近三门江国家森林公园");
		params.put("latitude", random.nextInt(80) + ".349354");
		params.put("longitude", random.nextInt(10) +"9.494114");
		return params;
	}
	
//	 public static void main(String[] args) {
//			System.out.println(new JuziFenqiSpider().checkTelephone("18210538513"));
//			System.out.println(new JuziFenqiSpider().checkTelephone("18210538577"));
//		}

    @Override
    public boolean checkTelephone(String account) {
        try {
            Session session = JJsoup.newSession();
            Connection.Response execute = session.connect("https://termib.juzifenqi.com/termi/dologin.do")
                    .headers(getHeader())
                    .ignoreContentType(true)
                    .data(getParams(account))
                    .method(Connection.Method.POST)
                    .execute();

            if (execute != null) {
                JSONObject result =JSON.parseObject(execute.body());
                if (result.getString("msg").contains("请输入正确的用户名或密码")) {
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
        return null;
    }

}
