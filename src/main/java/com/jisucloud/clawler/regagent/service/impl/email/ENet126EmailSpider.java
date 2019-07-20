package com.jisucloud.clawler.regagent.service.impl.email;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import java.util.Map;
import java.util.Set;

@UsePapaSpider
public class ENet126EmailSpider extends BasicEmailSpider {
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15010645316", "18210538513");
	}


    @Override
    public String message() {
        return "126邮箱是网易公司于2001年11月推出的免费的电子邮箱，是网易公司倾力打造的专业电子邮局，采用了创新Ajax技术，同等网络环境下，页面响应时间最高减少90%，垃圾邮件及病毒有效拦截率超过98%和99.8%。";
    }

    @Override
    public String platform() {
        return "126Email";
    }

    @Override
    public String home() {
        return "mail.126.com";
    }

    @Override
    public String platformName() {
        return "网易126邮箱";
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
	public String getEmail(String account) {
		// TODO Auto-generated method stub
		return account + "@126.com";
	}

}
