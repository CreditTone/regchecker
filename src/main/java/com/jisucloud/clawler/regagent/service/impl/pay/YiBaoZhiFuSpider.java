package com.jisucloud.clawler.regagent.service.impl.pay;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;

@Slf4j
@UsePapaSpider
public class YiBaoZhiFuSpider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "易宝支付是中国支付行业的开创者和领导者，也是互联网金融（ITFIN）和移动互联领军企业。易宝于2003年8月成立，总部位于北京，现有员工逾千人。";
	}

	@Override
	public String platform() {
		return "yeepay";
	}

	@Override
	public String home() {
		return "yeepay.com";
	}

	@Override
	public String platformName() {
		return "易宝支付";
	}

	@Override
	public String[] tags() {
		return new String[] {"聚合支付" , "互联网金融"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13193091202", "18210538513");
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#picCode");
				img.click();
				smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "n4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://mp.yeepay.com/app/merchantUserManagement/getBack/loginPwd");
			smartSleep(2000);
			chromeDriver.findElementByCssSelector("#userName").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				String imageCode = getImgCode();
				WebElement mark = chromeDriver.findElementByCssSelector("#validCode");
				mark.clear();
				mark.sendKeys(imageCode);
				chromeDriver.findElementByCssSelector("#submitConfirmButton").click();
				smartSleep(2000);
				if (vs) {
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
		return ct;
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
		return HookTracker.builder().addUrl("https://mp.yeepay.com/app/merchantUserManagement/getBack/validLoginName").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	
	boolean vs = false;
	boolean ct = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		vs = true;
		ct = contents.getTextContents().contains("member_mobile");
	}

}
