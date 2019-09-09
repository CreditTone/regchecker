package com.jisucloud.clawler.regagent.service.impl.social;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;


import java.util.Map;
import java.util.concurrent.TimeUnit;



@Slf4j
@PapaSpiderConfig(
		home = "yy.com", 
		message = "YY直播(yy.com)是中国最大的视频直播网站,yy语音官网,提供大神妹子英雄联盟,cf,dota等热门网络和单机游戏视频直播,美女帅哥视频秀场,同城视频互动交友,股票分析培训.速度流畅不卡,数万美女帅哥主播任你看.", 
		platform = "yy", 
		platformName = "YY直播", 
		tags = { "娱乐" , "交友" , "游戏" , "社区" }, 
		testTelephones = { "18810038000", "18210538513" })
public class YYSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(true, true);
			chromeDriver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
			chromeDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
			chromeDriver.getIgnoreTimeout("http://www.yy.com/h/38.html");
			chromeDriver.getIgnoreTimeout("https://zc.yy.com/reg/udb/reg4udb.do?appid=5719&action=3&type=Mobile&mode=udb&fromadv=yy_0.cpuid_0.channel_0&busiurl=http%3A%2F%2Fwww.yy.com%2F3rdLogin%2Freg-login.html");smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[node-name='inMobile']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[node-name='inPassword']").click();smartSleep(1000);
			return chromeDriver.findElementByCssSelector("div[node-name='mobile'] .form_tip").getText().contains("已注册");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTel;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
