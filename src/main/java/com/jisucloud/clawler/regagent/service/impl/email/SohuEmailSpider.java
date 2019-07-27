package com.jisucloud.clawler.regagent.service.impl.email;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import java.util.Map;
import java.util.Set;

@UsePapaSpider
public class SohuEmailSpider extends BasicEmailSpider {
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13010010001", "18210538513");
	}


    @Override
    public String message() {
        return "搜狐（ sohu）是中国四大门户网站之一，公司旗下推出的电子邮箱服务是目前国内最大的邮箱服务商之一。";
    }

    @Override
    public String platform() {
        return "sohuEmail";
    }

    @Override
    public String home() {
        return "mail.sohu.com";
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
        return "搜狐邮箱";
    }


	@Override
	public String getEmail(String account) {
		return account + "@sohu.com";
	}
}
