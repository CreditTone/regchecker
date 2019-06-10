package com.jisucloud.clawler.regagent.service.impl.money;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ZhongGuoRenMinBaoXianSpider implements PapaSpider {

	private ChromeDriver chromeDriver;

	@Override
	public String message() {
		return "人保官网直销省更多，安全有保障，无中间环节，车险多省15％，其他保险产品更优惠。优质理赔服务，享受与线下同等理赔服务，更可拥有网上投保专属特色服务；全国10万个网点，30万个专业理赔服务人员。方便快捷，条款、金额公开透明，自助选择，轻松对比，我的保险我做主。";
	}

	@Override
	public String platform() {
		return "epicc";
	}

	@Override
	public String home() {
		return "epicc.com";
	}

	@Override
	public String platformName() {
		return "PICC人保官网";
	}


	@Override
	public String[] tags() {
		return new String[] {"理财", "保险"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhongRenMinBaoXianSpider().checkTelephone("18210014444"));
//		System.out.println(new ZhongRenMinBaoXianSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36");
			chromeDriver.get("http://www.epicc.com.cn/newecenter/findPwd/find.do");
			Thread.sleep(5000);
			chromeDriver.findElementById("userName").sendKeys(account);
			chromeDriver.findElementByClassName("js-input-next").click();
			Thread.sleep(3000);
			Document doc = Jsoup.parse(chromeDriver.getPageSource());
			if (doc.select("#userMobile").attr("value").contains("****")) {
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
