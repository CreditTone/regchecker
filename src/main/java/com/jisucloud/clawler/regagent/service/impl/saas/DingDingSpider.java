package com.jisucloud.clawler.regagent.service.impl.saas;


import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.impl.email.BasicEmailSpider;
import com.jisucloud.clawler.regagent.util.PingyinUtil;

import java.util.Map;


@PapaSpiderConfig(
		home = "dingtalk.com", 
		message = "钉钉(DingTalk)是中国领先的智能移动办公平台,由阿里巴巴集团开发,免费提供给所有中国企业,用于商务沟通和工作协同。", 
		platform = "dinding", 
		platformName = "钉钉", 
		tags = {  "办公软件"  }, 
		testTelephones = { "13925300066", "18210538513" })
public class DingDingSpider extends BasicEmailSpider {

	private String name;

	public boolean checkEmail(String account) {
		return false;
	}

	public String getEmail(String account) {
		if (name != null) {
			String pingyin = PingyinUtil.toPinyin(name);
			return pingyin + account.substring(account.length() - 4) + "@dingtalk.com";
		}
		return null;
	}
}
