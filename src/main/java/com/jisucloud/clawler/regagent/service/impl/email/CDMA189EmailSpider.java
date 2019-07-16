package com.jisucloud.clawler.regagent.service.impl.email;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import java.util.Map;
import java.util.Set;

@UsePapaSpider
public class CDMA189EmailSpider extends BasicEmailSpider {


    @Override
    public String message() {
        return "189免费邮箱是面向所有互联网用户的新型工作和商务邮箱,覆盖Web、Wap、 Pad、iOS、Android等多个终端,满足用户随时随地处理邮件需要。";
    }

    @Override
    public String platform() {
        return "189Email";
    }

    @Override
    public String home() {
        return "mail.189.cn";
    }

    @Override
    public String platformName() {
        return "电信189邮箱";
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
		return new String[] {"邮箱"};
	}
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15010645316", "18210538513");
	}

	@Override
	public String getEmail(String account) {
		return account + "@189.cn";
	}



}
