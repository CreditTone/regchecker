package com.jisucloud.clawler.regagent.service.impl.borrow;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

@PapaSpiderConfig(
		home = "pingan.com", 
		message = "平安小额贷对用户借款需求进行智能匹配，为用户推荐更适合的低费率、高额度放款快的贷款产品。 业务涵盖小额贷款、金融贷款、极速现金贷款、大额贷款、信用卡贷款、等信贷服务及信用卡办理。", 
		platform = "pinganxd", 
		platformName = "平安小额贷", 
		tags = {"P2P", "借贷" }, 
		testTelephones = {"18810038000", "18210538513" },
		exclude = true)
public class PingAnXiaoDaiSpdier extends PapaSpider {


	@Override
	public boolean checkTelephone(String account) {
//		try {
//			String body = JJsoup.newSession().connect("http://18.162.179.74:8080/zhita_daichao_app/app_login/pwdlogin").requestBody(
//					"phone="+account+"&pwd=shadiaoXX&company=%E5%80%9F%E5%90%A7&oneSourceName=%E5%B9%B3%E5%AE%89%E5%B0%8F%E9%A2%9D%E8%B4%B7&twoSourceName=yingyongbao")
//					.method(Connection.Method.POST).execute().body();
//			System.out.println(body);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

}
