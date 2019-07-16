package com.jisucloud.clawler.regagent.service.impl.borrow;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import me.kagura.JJsoup;
import org.jsoup.Connection;

@UsePapaSpider
public class PingAnXiaoDaiSpdier extends PapaSpider {

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return "平安小额贷对用户借款需求进行智能匹配，为用户推荐更适合的低费率、高额度放款快的贷款产品。 业务涵盖小额贷款、金融贷款、极速现金贷款、大额贷款、信用卡贷款、等信贷服务及信用卡办理。";
	}

	@Override
	public String platform() {
		// TODO Auto-generated method stub
		return "pinganxd";
	}

	@Override
	public String home() {
		// TODO Auto-generated method stub
		return "pingan.com";
	}

	@Override
	public String platformName() {
		// TODO Auto-generated method stub
		return "平安小额贷";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String body = JJsoup.newSession().connect("http://18.162.179.74:8080/zhita_daichao_app/app_login/pwdlogin").requestBody(
					"phone="+account+"&pwd=shadiaoXX&company=%E5%80%9F%E5%90%A7&oneSourceName=%E5%B9%B3%E5%AE%89%E5%B0%8F%E9%A2%9D%E8%B4%B7&twoSourceName=yingyongbao")
					.method(Connection.Method.POST).execute().body();
			System.out.println(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
		return new String[] {"P2P", "借贷"};
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18810038000", "18210538513");
	}

}
