package com.jisucloud.clawler.regagent.service.impl.game;

import com.jisucloud.clawler.regagent.http.PersistenceCookieJar;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
public class XuebaoSpider implements PapaSpider {
	
	private ChromeDriver chromeDriver;

	@Override
	public String message() {
		return "暴雪娱乐公司是一家著名视频游戏制作和发行公司,1991年2月8日由加利福尼亚大学洛杉矶分校的三位毕业生1994年,他们公司品牌正式更名为“Blizzard” 在“暴雪”成立.";
	}

	@Override
	public String platform() {
		return "battlenet";
	}

	@Override
	public String home() {
		return "battlenet.com";
	}

	@Override
	public String platformName() {
		return "暴雪娱乐";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏" ,"魔兽世界"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new XuebaoSpider().checkTelephone("18210538513"));
//		System.out.println(new XuebaoSpider().checkTelephone("18210538511"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
			chromeDriver.get("https://www.battlenet.com.cn/login/zh/?ref=https://www.battlenet.com.cn/zh/&app=com-root");
			Thread.sleep(2000);
			chromeDriver.findElementById("accountName").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("woxiaoxoa132");
			chromeDriver.findElementById("submit").click();
			Thread.sleep(3000);
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
