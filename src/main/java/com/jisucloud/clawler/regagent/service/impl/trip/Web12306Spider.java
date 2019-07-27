package com.jisucloud.clawler.regagent.service.impl.trip;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deep007.spiderbase.okhttp.OKHttpUtil;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UsePapaSpider
public class Web12306Spider extends PapaSpider {
	
	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();
	
    private Map<String, String> fields = null;
    
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("19910002005", "18210538513");
	}

    @Override
    public String message() {
        return "中国铁路客户服务中心（12306网）是铁路服务客户的重要窗口，将集成全路客货运输信息，为社会和铁路客户提供客货运输业务和公共信息查询服务。客户通过登录本网站，可以查询旅客列车时刻表、票价、列车正晚点、车票余票、售票代售点、货物运价、车辆技术参数以及有关客货运规章。";
    }

    @Override
    public String platform() {
        return "12306";
    }

    @Override
    public String home() {
        return "12306.cn";
    }

    private Headers getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "kyfw.12306.cn");
        headers.put("Referer", "https://kyfw.12306.cn/otn/regist/init");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return Headers.of(headers);
    }

    private FormBody getParams(String mobile, String email) {
        FormBody formBody = new FormBody
                .Builder()
        .add("loginUserDTO.user_name", "wx18910674321")
        .add("userDTO.password", "qqadmin127")
        .add("confirmPassWord", "qqadmin127")
        .add("loginUserDTO.id_type_code", "1")
        .add("idTypeRadio", "1")
        .add("loginUserDTO.name", "孙良成")
        .add("loginUserDTO.id_no", "320219196503306777")
        .add("loginUserDTO.GAT_valid_date_end", "")
        .add("userDTO.born_date", "")
        .add("userDTO.country_code", "CN")
        .add("userDTO.email", email)
        .add("userDTO.mobile_no", mobile)
        .add("passenger_type", "1")
        .add("studentInfoDTO.province_code", "1")
        .add("studentInfoDTO.school_code", "")
        .add("studentInfoDTO.school_name", "简码/汉字")
        .add("studentInfoDTO.department", "")
        .add("studentInfoDTO.school_class", "")
        .add("studentInfoDTO.student_no", "")
        .add("studentInfoDTO.school_system", "1")
        .add("studentInfoDTO.enter_year", "2019")
        .add("studentInfoDTO.preference_card_no", "")
        .add("studentInfoDTO.preference_from_station_name", "简码/汉字")
        .add("studentInfoDTO.preference_from_station_code", "")
        .add("studentInfoDTO.preference_to_station_name", "简码/汉字")
        .add("studentInfoDTO.preference_to_station_code", "")
        .build();
        return formBody;
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
        	okHttpClient.newCall(createRequest("https://www.12306.cn/index/")).execute().body().close();
        	okHttpClient.newCall(createRequest("https://kyfw.12306.cn/otn/regist/init")).execute().body().close();
			Request request = new Request.Builder().url("https://kyfw.12306.cn/otn/regist/getRandCode")
					.headers(getHeader())
					.post(getParams(account, ""))
					.build();
			JSONObject result = JSON.parseObject(okHttpClient.newCall(request).execute().body().string());
            System.out.println(result);
            if (result.getBooleanValue("status") == true) {
                String msg = result.getJSONObject("data").getString("msg");
                if (msg != null && msg.contains("您输入的手机号码已被其他注册用户")) {
                    Matcher matcher = Pattern.compile("其他注册用户（([^）]+)）使用").matcher(msg);
                    if (matcher.find()) {
                        fields = new HashMap<>();
                        fields.put("name", matcher.group(1));
                    }
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
        return fields;
    }

    @Override
    public String platformName() {
        return "12306";
    }

    @Override
	public String[] tags() {
		return new String[] {"出行" , "火车" , "高铁"};
	}

}
