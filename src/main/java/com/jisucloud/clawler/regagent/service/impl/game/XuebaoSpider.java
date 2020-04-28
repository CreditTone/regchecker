package com.jisucloud.clawler.regagent.service.impl.game;

import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@PapaSpiderConfig(
		home = "battlenet.com", 
		message = "暴雪娱乐公司是一家著名视频游戏制作和发行公司,1991年2月8日由加利福尼亚大学洛杉矶分校的三位毕业生1994年,他们公司品牌正式更名为“Blizzard” 在“暴雪”成立.", 
		platform = "battlenet", 
		platformName = "暴雪娱乐", 
		tags = { "游戏" ,"魔兽世界" }, 
		testTelephones = { "18212345678", "13269423806" })
public class XuebaoSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://www.battlenet.com.cn/login/zh/?ref=https://www.battlenet.com.cn/zh/&app=com-root");smartSleep(2000);
			chromeDriver.findElementById("accountName").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("woxiaoxoa132");
			chromeDriver.findElementById("submit").click();smartSleep(3000);
			Document doc = Jsoup.parse(chromeDriver.getPageSource());
			String res = doc.select("#display-errors").text();
			if (res.contains("密码有误") || res.contains("密码")) {
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
