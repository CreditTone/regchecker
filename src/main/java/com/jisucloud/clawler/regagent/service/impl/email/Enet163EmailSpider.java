package com.jisucloud.clawler.regagent.service.impl.email;


import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;


@PapaSpiderConfig(
		home = "mail.163.com", 
		message = "网易公司（NASDAQ: NTES）是中国的互联网公司，利用互联网技术，加强人与人之间信息的交流和共享，实现“网聚人的力量”。创始人兼CEO是丁磊。", 
		platform = "enet", 
		platformName = "网易163邮箱", 
		tags = { "邮箱" }, 
		testTelephones = { "15010645316", "18212345678" },
		ignoreTestResult = true)
public class Enet163EmailSpider extends BasicEmailSpider {

    @Override
    public boolean checkEmail(String account) {
        return false;
    }

	@Override
	public String getEmail(String account) {
		return account + "@163.com";
	}
}
