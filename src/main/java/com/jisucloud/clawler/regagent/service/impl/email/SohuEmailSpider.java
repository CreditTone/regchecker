package com.jisucloud.clawler.regagent.service.impl.email;

import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;

@PapaSpiderConfig(
		home = "mail.sohu.com", 
		message = "搜狐（ sohu）是中国四大门户网站之一，公司旗下推出的电子邮箱服务是目前国内最大的邮箱服务商之一。", 
		platform = "sohuEmail", 
		platformName = "搜狐邮箱", 
		tags = { "邮箱" }, 
		testTelephones = { "13010010001", "18210538513" },
		ignoreTestResult = true)
public class SohuEmailSpider extends BasicEmailSpider {
	

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
		return account + "@sohu.com";
	}
}
