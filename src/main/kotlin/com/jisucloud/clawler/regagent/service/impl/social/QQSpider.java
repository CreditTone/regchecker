package com.jisucloud.clawler.regagent.service.impl.social;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import me.kagura.Session;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QQSpider implements PapaSpider {


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

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "aq.qq.com");
        headers.put("Referer", "https://aq.qq.com/v2/uv_aq/html/reset_pwd/pc_reset_pwd_input_account.html?v=");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("TE", "Trailers");
        return headers;
    }


    @Override
    public boolean checkTelephone(String account) {
        try {
            Session session = JJsoupUtil.newProxySession();
            session.connect("https://aq.qq.com/v2/uv_aq/html/reset_pwd/pc_reset_pwd_input_account.html?v=3.0&old_ver_account=").execute();

            Connection.Response response = session.connect("https://aq.qq.com/cn2/reset_pwd/pc/pc_reset_pwd_get_uin_by_input_ajax?aq_account=" + account + "&qq_txwb_user_choice=0&_=" + System.currentTimeMillis())
                    .headers(getHeader())
                    .ignoreContentType(true)
                    .execute();

            if (response != null) {
                JSONObject result = JSON.parseObject(response.body());
                System.out.println(result);
                if (result.getString("ret").equals("0")) {
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
        return false;
    }

    @Override
    public Map<String, String> getFields() {
        return null;
    }

    @Override
    public String platformName() {
        return "腾讯QQ";
    }

    @Override
    public Map<String, String[]> tags() {
        return null;
    }
}
