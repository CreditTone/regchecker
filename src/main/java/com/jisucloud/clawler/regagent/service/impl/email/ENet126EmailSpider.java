package com.jisucloud.clawler.regagent.service.impl.email;


import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;


@PapaSpiderConfig(
		home = "mail.126.com", 
		message = "126邮箱是网易公司于2001年11月推出的免费的电子邮箱，是网易公司倾力打造的专业电子邮局，采用了创新Ajax技术，同等网络环境下，页面响应时间最高减少90%，垃圾邮件及病毒有效拦截率超过98%和99.8%。", 
		platform = "126Email", 
		platformName = "网易126邮箱", 
		tags = { "邮箱" }, 
		testTelephones = { "15010645316", "18212345678" },
		ignoreTestResult = true)
public class ENet126EmailSpider extends BasicEmailSpider {
	

    @Override
    public boolean checkEmail(String account) {
        return false;
    }

    @Override
    public Map<String, String> getFields() {
        return null;
    }

	@Override
	public String getEmail(String account) {
		// TODO Auto-generated method stub
		return account + "@126.com";
	}

}
