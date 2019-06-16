package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class JinRongGongChangSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "金融工场-是中国领先的综合金融信息服务平台。金融工场以金融全球化发展趋势为契机,融合信息技术创新手段,秉承安全、专业、透明的经营理念,为用户提供多样化高效智能的。";
	}

	@Override
	public String platform() {
		return "9888keji";
	}

	@Override
	public String home() {
		return "9888keji.com";
	}

	@Override
	public String platformName() {
		return "金融工场";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new JinRongGongChangSpider().checkTelephone("13910252043"));
//		System.out.println(new JinRongGongChangSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeImg");
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
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.quicklyVisit("https://passport.9888keji.com/passport/login?service=https%3A%2F%2Fwww.9888keji.com%2ForderUser%2Flogin.shtml");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "passport/login";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					vcodeSuc = true;
					checkTel = ajax.getResponse().contains("账号或密码错误") || ajax.getResponse().contains("锁定");
				}
			});
			chromeDriver.findElementById("username").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("lvnqwnk12mcxn");
			for (int i = 0; i < 5; i++) {
				chromeDriver.reInject();
				chromeDriver.findElementByCssSelector("input[class='g-btn-login bl']").click();
				Thread.sleep(3000);
				if (vcodeSuc) {
					break;
				}
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
