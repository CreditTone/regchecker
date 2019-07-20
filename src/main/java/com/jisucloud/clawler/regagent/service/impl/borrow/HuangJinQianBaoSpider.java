package com.jisucloud.clawler.regagent.service.impl.borrow;

import java.util.Map;
import java.util.Set;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

@UsePapaSpider
public class HuangJinQianBaoSpider extends PapaSpider {

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return "黄金钱包是领先风投软银中国投资的互联网实物黄金投资、消费平台，为普通消费者和投资者提供“低价买黄金”、“黄金商城”和“黄金回收”等一站式服务，价格实时直通上海黄金交易所，最低买入或卖出单位为1克";
	}

	@Override
	public String platform() {
		// TODO Auto-generated method stub
		return "gbanker";
	}

	@Override
	public String home() {
		// TODO Auto-generated method stub
		return "g-banker.com";
	}

	@Override
	public String platformName() {
		// TODO Auto-generated method stub
		return "黄金钱包";
	}

	@Override
	public boolean checkTelephone(String account) {
		// TODO Auto-generated method stub
		try {
			String body = Jsoup.connect("https://www.g-banker.com/user/updateUserLogin")
					.requestBody("{\"telephone\":\""+account+"\",\"password\":\"ed89w89dwe\"}")
					.header("Content-Type", "application/json;charset=UTF-8").method(Connection.Method.POST)
					.ignoreContentType(true).execute().body();
			return !body.contains("手机号未注册");
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
		return new String[] { "P2P", "借贷" };
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252000", "18210538513");
	}

}
