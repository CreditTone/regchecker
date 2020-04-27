package com.jisucloud.clawler.regagent.service.impl.shop;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@PapaSpiderConfig(
		home = "benlai.com", 
		message = "本来生活网,自建仓储物流,冷链配送,全部食品严格把关,经过安全检测,品质保证,48小时退换货,提供进口水果,应季水果,有机蔬菜,牛羊肉,粮油,褚橙,红酒,车厘子等.", 
		platform = "benlai", 
		platformName = "本来生活网", 
		tags = { "电商" , "农产品" }, 
		testTelephones = { "18800000001", "18212345678" })
public class BenlaiShenghuoSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, ANDROID_USER_AGENT);
			chromeDriver.get("https://m.benlai.com/showlogin?otherLogin=1&loginType=2&backType=&comeFromApp=0&afterUrl=");
			smartSleep(2000);
			chromeDriver.findElementById("customerID").sendKeys(account);
			chromeDriver.findElementById("customerPwd").sendKeys("woxiaoxoa132");
			chromeDriver.findElementById("loginBtn").click();
			smartSleep(3000);
			Document doc = Jsoup.parse(chromeDriver.getPageSource());
			String res = doc.select("#baseErrorMsg").text();
			if (res.contains("用户名不存在")) {
				return false;
			}else if (res.contains("验证码输入错误") || res.contains("密码")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return false;
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
