package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@UsePapaSpider
public class LuJinSuoSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
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
		return Sets.newHashSet("13900002045", "18210538513");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("img.captcha_img");
				img.click();smartSleep(1000);
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
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(false, true);
			chromeDriver.get("https://m.lu.com/user/login?&returnToken=true&to=https%3A%2F%2Fpromo.lu.com%2Flupromo%2Factivity%2Fh5%2FLP_20181220_xiazai%3FutmSource&execOrderRegist=r#/");
			chromeDriver.addAjaxHook(this);
			WebElement identitycard = chromeDriver.findElementByCssSelector("input[class='input-item identitycard u-full']");
			identitycard.sendKeys(account);
			WebElement password = chromeDriver.findElementByCssSelector("input[class='input-item password u-full']");
			password.sendKeys("xkam2nxclasns1");
			smartSleep(1000);
			chromeDriver.mouseClick(chromeDriver.findElementByClassName("Checkbox-left"));
			for (int i = 0; i < 6; i++) {
				if (chromeDriver.checkElement(".Dialog")) {
					chromeDriver.findElementByCssSelector(".Dialog .flex-full").click();
					smartSleep(1000);
				}
				WebElement validate = chromeDriver.findElementByCssSelector("input[class='css_input captcha u-full']");
				chromeDriver.keyboardInput(validate, getImgCode());
				chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("div.next-b1"));
				smartSleep(3000);
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
		smartSleep(3000);
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

	@Override
	public HookTracker getHookTracker() {
		return HookTracker.builder().addUrl("/login/union-login").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (!contents.getTextContents().contains("验证码过期或错误")) {
			times ++;
			if (contents.getTextContents().contains("连续输错5次登录密码")) {
				times += 100;
			}
			lastMsg = contents.getTextContents();
		}
	}

}
