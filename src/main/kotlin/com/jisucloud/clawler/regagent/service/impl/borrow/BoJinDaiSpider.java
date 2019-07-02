package com.jisucloud.clawler.regagent.service.impl.borrow;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import org.jsoup.Connection;
import me.kagura.JJsoup;
import me.kagura.Session;

@UsePapaSpider
public class BoJinDaiSpider implements PapaSpider {

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return "博金贷(www.bjdp2p.com)互联网金融服务平台,由博能控股集团,江西省投资集团,大成国资,南治资产,南昌市小额贷款公司协会等共同出资组建并获得中国网贷平台。";
	}

	@Override
	public String platform() {
		// TODO Auto-generated method stub
		return "bjdp2p";
	}

	@Override
	public String home() {
		// TODO Auto-generated method stub
		return "bjdp2p.com";
	}

	@Override
	public String platformName() {
		// TODO Auto-generated method stub
		return "博金贷";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			Session session = JJsoup.newSession();
			String body = session.connect("https://www.bjdp2p.com/checkPhone.page?phoneNumber=" + account)
				.method(Connection.Method.POST)
				.header("Referer", "https://www.bjdp2p.com/regist.page")
				.header("X-Requested-With", "XMLHttpRequest")
				.ignoreContentType(true)
				.execute().body();
			return body.contains("已存在");
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
		// TODO Auto-generated method stub
		return new String[] {"P2P", "借贷"};
	}

	@Override
	public Set<String> getTestTelephones() {
		// TODO Auto-generated method stub
		return Sets.newHashSet("13261165342", "18210538513");
	}

}
