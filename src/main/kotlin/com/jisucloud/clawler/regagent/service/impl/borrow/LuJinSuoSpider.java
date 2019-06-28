package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.mockito.internal.util.collections.Sets;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@UsePapaSpider
public class LuJinSuoSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private int times = 0;//成功尝试登录次数:验证码正确即成功
	private String lastMsg = "";

	@Override
	public String message() {
		return "陆金所(LU.com)-中国平安集团倾力打造的投资理财平台。在健全的风险管控体系基础上,为投资者提供专业理财服务。2018年毕马威全球金融科技前10强 ,多样化理财产品满足您全方位财富管理需求:零钱理财,期限理财,网贷,基金等。立即注册，开启理财生活。";
	}

	@Override
	public String platform() {
		return "lu";
	}

	@Override
	public String home() {
		return "lu.com";
	}

	@Override
	public String platformName() {
		return "陆金所";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷" , "理财"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("13900002045", "18210538513");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("img.captcha_img");
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
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A402 Safari/604.1");
			chromeDriver.quicklyVisit("https://m.lu.com/user/login?&returnToken=true&to=https%3A%2F%2Fpromo.lu.com%2Flupromo%2Factivity%2Fh5%2FLP_20181220_xiazai%3FutmSource&execOrderRegist=r#/");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "/login/union-login";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					System.out.println(ajax);
					if (!ajax.getResponse().contains("验证码过期或错误")) {
						times ++;
						if (ajax.getResponse().contains("连续输错5次登录密码")) {
							times += 100;
						}
						lastMsg = ajax.getResponse();
					}
				}

				@Override
				public String fixPostData() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String fixGetData() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			WebElement identitycard = chromeDriver.findElementByCssSelector("input[class='input-item identitycard u-full']");
			//chromeDriver.keyboardInput(identitycard, account);
			identitycard.sendKeys(account);
			WebElement password = chromeDriver.findElementByCssSelector("input[class='input-item password u-full']");
			//chromeDriver.keyboardInput(password, "xkam2nxclasns1");
			password.sendKeys("xkam2nxclasns1");
			Thread.sleep(1000);
			chromeDriver.mouseClick(chromeDriver.findElementByClassName("Checkbox-left"));
			for (int i = 0; i < 6; i++) {
				if (chromeDriver.checkElement(".Dialog")) {
					chromeDriver.findElementByCssSelector(".Dialog .flex-full").click();
					Thread.sleep(1000);
				}
				WebElement validate = chromeDriver.findElementByCssSelector("input[class='css_input captcha u-full']");
				chromeDriver.keyboardInput(validate, getImgCode());
				chromeDriver.reInject();
				chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("div.next-b1"));
				Thread.sleep(3000);
				if (times >= 2) {
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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Matcher matcher = Pattern.compile("可以输入(\\d+)次").matcher(lastMsg);
		if (matcher.find()) {
			int dtimes = Integer.parseInt(matcher.group(1));
			return dtimes < 5;
		}
		return lastMsg.contains("连续输错5次登录密码");
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
