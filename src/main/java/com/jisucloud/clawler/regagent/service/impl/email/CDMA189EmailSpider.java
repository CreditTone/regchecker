package com.jisucloud.clawler.regagent.service.impl.email;


import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;


@PapaSpiderConfig(
		home = "mail.189.cn", 
		message = "189免费邮箱是面向所有互联网用户的新型工作和商务邮箱,覆盖Web、Wap、 Pad、iOS、Android等多个终端,满足用户随时随地处理邮件需要。", 
		platform = "189Email", 
		platformName = "189邮箱", 
		tags = { "邮箱" }, 
		testTelephones = { "15010645316", "18210538513" },
		ignoreTestResult = true)
public class CDMA189EmailSpider extends BasicEmailSpider {

    
    public boolean checkEmail(String account) {
        return false;
    }

	public String getEmail(String account) {
		return account + "@189.cn";
	}

}
