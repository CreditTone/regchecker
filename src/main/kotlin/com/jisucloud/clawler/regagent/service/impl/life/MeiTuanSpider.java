package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Slf4j
@UsePapaSpider
public class MeiTuanSpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "美团网精选美食餐厅,酒店预订,电影票,旅游景点,外卖订餐,团购信息,您可查询商家评价店铺信息。生活,下载美团官方APP ,吃喝玩乐1折起。";
	}

	@Override
	public String platform() {
		return "meituan";
	}

	@Override
	public String home() {
		return "meituan.com";
	}

	@Override
	public String platformName() {
		return "美团网";
	}

	@Override
	public String[] tags() {
		return new String[] {"o2o", "外卖", "电影票" , "酒店" , "共享单车"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new MeiTuanSpider().checkTelephone("18210538513"));
//		System.out.println(new MeiTuanSpider().checkTelephone("18210530000"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("span[class='refresh kv-v'] img");
				img.click();
				Thread.sleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			chromeDriver.get("https://i.meituan.com/risk2/resetreq");
			Thread.sleep(2000);
			for (int i = 0 ; i < 5 ; i ++) {
				WebElement nameInputArea = chromeDriver.findElementByCssSelector("input[name='user']");
				nameInputArea.sendKeys(account);
				WebElement captcha = chromeDriver.findElementByCssSelector("input[name='captcha']");
				captcha.sendKeys(getImgCode());
				chromeDriver.findElementByCssSelector("button[type='submit']").click();
				Thread.sleep(2000);
				String res = chromeDriver.getPageSource();
				if (res.contains("验证码不正确")) {
					continue;
				}
				checkTel = chromeDriver.getPageSource().contains("选择验证方式");
				break;
			}
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
