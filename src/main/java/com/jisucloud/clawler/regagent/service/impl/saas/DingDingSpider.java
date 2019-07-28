package com.jisucloud.clawler.regagent.service.impl.saas;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.service.impl.email.BasicEmailSpider;
import com.jisucloud.clawler.regagent.util.PingyinUtil;

import java.util.Map;
import java.util.Set;

@UsePapaSpider
public class DingDingSpider extends BasicEmailSpider {

	private String name;

	@Override
	public String message() {
		return "钉钉(DingTalk)是中国领先的智能移动办公平台,由阿里巴巴集团开发,免费提供给所有中国企业,用于商务沟通和工作协同。";
	}

	@Override
	public String platform() {
		return "dinding";
	}

	@Override
	public String home() {
		return "dingtalk.com";
	}

	@Override
	public String platformName() {
		return "钉钉";
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13925300066", "18210538513");
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
	public String[] tags() {
		return new String[] { "办公软件" };
	}

	@Override
	public String getEmail(String account) {
		if (name != null) {
			String pingyin = PingyinUtil.toPinyin(name);
			return pingyin + account.substring(account.length() - 4) + "@dingtalk.com";
		}
		return null;
	}
}
