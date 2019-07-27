package com.jisucloud.clawler.regagent.service.impl.email;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import java.util.Map;
import java.util.Set;

@UsePapaSpider
public class Enet163EmailSpider extends BasicEmailSpider {
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15010645316", "18210538513");
	}


    @Override
    public String message() {
        return "网易公司（NASDAQ: NTES）是中国的互联网公司，利用互联网技术，加强人与人之间信息的交流和共享，实现“网聚人的力量”。创始人兼CEO是丁磊。";
    }

    @Override
    public String platform() {
        return "enet";
    }

    @Override
    public String home() {
        return "mail.163.com";
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
    public String platformName() {
        return "网易163邮箱";
    }

	@Override
	public String getEmail(String account) {
		return account + "@163.com";
	}
}
