package com.jisucloud.clawler.regagent.service.impl.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class XiaomiSpider implements PapaSpider {

    @Override
    public String message() {
        return "米商城直营小米公司旗下所有产品,囊括小米手机系列小米9、小米MIX 3、小米Play,Redmi 红米系列Redmi K20、Redmi Note 7、Redmi 7A,智能硬件,配件及小米生活周边,。";
    }

    @Override
    public String platform() {
        return "xiaomi";
    }

    @Override
    public String home() {
        return "xiami.com";
    }

    private Map<String, String> getHeader(String account) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "account.xiaomi.com");
        headers.put("Referer", "https://account.xiaomi.com/auth/service?user=" + account + "&_sign=2%26V1_passport%26%2B5nAEgHs%2BPwL9fkw6Pj2sJaO9DM%3D&session=V3TxF%2FJ%2FjIs39WTO2fiN0JCKwM%2B3U%2BqmCrlJinJwEJ9XDUzXWPjim54zC2Byr4v4zH5Gn9QiCN07TMXQhojmSdXTX66O08ix0DsyHX%2BWzZnECKwJ57NQr%2Frpw7ifMJN%2BnErcfZOYRUsOYVrxBPQciEKPDM4IoDwrNGhJc4%2BBowva5yjOdVck%2F0b3K8rSj0qVuWGvZaUqcyEszJlJ4yawKK3x9%2FmhS%2B4FXxrDW8ehw%2FSTNnBLEQhFjiMi7XkloBCOfFXLn7TzevQWTRHG9q%2B9KYkmyONa2%2B4%2B5r7JpLUdsqrNpm%2FSlZVXM1Y9DGGlnk%2FhyL6xiX3eyoH7npdDWLFpTlrCb4PcJ25fFpwDkuS6o7cLS8dlH3bgH4JLIaGOoNfYOCr%2BeIkJ%2F523ajyuBpWworkdy75JXjU5pKH3BMurFWynnD6xG0icdixnfMtiG2TRXrpd5I3KOVsdanNn5Mu5fJ8apyn9TXSibUI4tbJu3ZN7O%2F4hvaoXSNnLXCyX9sez57I3jGTQInGAts4C7ZtqDfhYN4zJCEa%2BDhIaNdM1AFkf%2F4GmfZ6%2FnJw9nHDtIA7ZAVi7%2BaK5bdZKADGYvjrIW297Pwb5ABvkhidpxqyhztedL1ubIgT9thpg6r3SuG3P0vuOE0xacDnNc5aFqci8Okvo2qphCoL5e4v5bNH2bIkmPJUsBxLLctE%2Fh5wnCDKCmqrAAWABoWoXOP1pgHz7Ph%2Faosa0TN7ZfhXilBO0li9Re5pmOFkZUTkJ9mQMBpePW25mwNxKbsy4TAD9YLy6xPBRe34R67w7ImXX2LeEdHQLJkXoYTPpKK%2Fun3ylgkiQxJY7ZMgGDOihJtRbzQhzlZk7DYl71IOv%2BXLC1zwJnfQcfdUrb0nJMWlirzoL8R52FLjK57UNq%2B76EcmUKzWgcTAaKSHN6C6dxXC%2BJ9VpKpiFJzwkAJj%2F0p0FnIiOEPm4JmagSlUZ%2BZY%2BHu8E2JhNJw%3D%3D&nonce=CiNX52HCcUIBitYB");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    private Map<String, String> getParams(String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put("contentType", "160039");
        params.put("addressType", "PH");
        params.put("user", mobile);
        return params;
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            Map<String, String> cookies = JJsoupUtil.newProxySession().connect("https://account.xiaomi.com/pass/forgetPassword")
                    .ignoreContentType(true)
                    .execute().cookies();
            String url = "https://account.xiaomi.com/pass/sms/userQuota?_dc=" + System.currentTimeMillis();
            Connection.Response response = Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .cookies(cookies)
                    .headers(getHeader(account))
                    .data(getParams(account))
                    .ignoreContentType(true)
                    .execute();

            if (response != null) {
                JSONObject result = JSON.parseObject(response.body().replaceAll("&&&START&&&", ""));
                System.out.println(result);
                if (result.getIntValue("code") == 0) {
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
        return "小米手机云";
    }

    @Override
	public String[] tags() {
		return new String[] {"电商" , "智能手机"};
	}


}
