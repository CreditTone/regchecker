package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class GongChangJinRongSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "工场微金由北京凤凰信用管理有限公司管理运营,专注于向借款人及出借人(用户)提供撮合服务的网络借贷信息中介平台。平台作为首批中国互联网金融协会成员单位及北京网贷。";
	}

	@Override
	public String platform() {
		return "gongchangp2p";
	}

	@Override
	public String home() {
		return "gongchangp2p.com";
	}

	@Override
	public String platformName() {
		return "工场微金";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13879690000", "18210538513");
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#kaptchaImage");
				chromeDriver.mouseClick(img);smartSleep(1000);
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
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(false, true, CHROME_USER_AGENT);
			chromeDriver.get("https://www.gongchangp2p.com");
			chromeDriver.get("https://www.gongchangp2p.com/depository/retrieve/toRetrievePwd.shtml?userType=0");
			smartSleep(2000);
			chromeDriver.keyboardInput(chromeDriver.findElementByCssSelector("input[id='phone']"), account);
			for (int i = 0; i < 5; i++) {
				String imageCode = getImgCode();
				if (!imageCode.isEmpty()) {
					WebElement codeInput = chromeDriver.findElementByCssSelector("input[id='x_yanzhengma']");
					chromeDriver.keyboardInput(codeInput, imageCode);
				}
				WebElement next = chromeDriver.findElementByCssSelector("input[id='next']");
				chromeDriver.mouseClick(next);smartSleep(3000);//Error_Tip-cnt
				Document doc = Jsoup.parse(chromeDriver.getPageSource());
				if (doc.select("#hs-findPsw-cont1").text().contains("确认新密码")) {
					return true;
				}
				if (chromeDriver.getCurrentUrl().contains("toRetrievePwd.shtml") && chromeDriver.findElementById("Error_Tip-cnt").isDisplayed()) {
					//验证码错误
					continue;
				}
				break;
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
