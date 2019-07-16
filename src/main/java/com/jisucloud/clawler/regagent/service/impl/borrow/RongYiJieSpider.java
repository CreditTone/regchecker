package com.jisucloud.clawler.regagent.service.impl.borrow;

import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import me.kagura.JJsoup;
import me.kagura.Session;

@UsePapaSpider
public class RongYiJieSpider extends PapaSpider {

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return "容易借APP搜罗新全的小额极速贷、大额分期贷、新口子、。为你推荐更低利率，更快放款的贷款口子。下款率高达90%，快至30分钟放款。";
	}

	@Override
	public String platform() {
		// TODO Auto-generated method stub
		return "casheasy";
	}

	@Override
	public String home() {
		// TODO Auto-generated method stub
		return "casheasy.cn";
	}

	@Override
	public String platformName() {
		// TODO Auto-generated method stub
		return "借钱花呗";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			Session session = JJsoup.newSession();
			 String body = session.connect("http://www.casheasy.cn:8082/login?username="+account+"&password=j9gIeoM%2BIU9%2BySW3C8hhPA%3D%3D")
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute().body();
            //{"body":null,"header":{"requestDate":"uQNfetqztYz9W8ee3yPrNQ\u003d\u003d","requestTime":"Vk0jgvEesOQ\u003d","requestId":null,"respCode":"84AwJuHl8ac\u003d","respMsg":"7ikiK/ueAXJ0F/Kl0m+5CFrwNKoE4fnA"}}
            System.err.println(body);
            return !body.contains("7ikiK/ueAXJ0F/Kl0m+5CFrwNKoE4fnA");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public boolean checkEmail(String account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] tags() {
		return new String[] { "P2P", "借贷" };
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252000", "18210538513");
	}
}
