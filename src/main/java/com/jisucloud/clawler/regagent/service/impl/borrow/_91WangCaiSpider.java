package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class _91WangCaiSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "91旺财是九一金融旗下互联网网络借贷信息中介平台,北京市互联网金融行业协会副会长单位,中国互联网金融协会会员理事单位,公司法人许泽玮先生现任北京市互联网金融协会。";
	}

	@Override
	public String platform() {
		return "91wangcai";
	}

	@Override
	public String home() {
		return "91wangcai.com";
	}

	@Override
	public String platformName() {
		return "91旺财";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252000", "18210538513");
	}
	

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeImg");
				img.click();
				smartSleep(1000);
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
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.get("https://www.91wangcai.com/user/to_login");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("username").sendKeys(account);
			chromeDriver.findElementById("pwd").sendKeys("lvnqwnk12mcxn");
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("exa");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.findElementById("login_btn").click();
				smartSleep(3000);
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

	@Override
	public HookTracker getHookTracker() {
		return HookTracker.builder().addUrl("oauth2/authorize").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (!contents.getTextContents().contains("验证码错误")) {
			vcodeSuc = true;
			checkTel = contents.getTextContents().contains("密码错误") || contents.getTextContents().contains("锁定");
		}
	}

}
