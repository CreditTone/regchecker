package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.GifDecoder;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.mockito.internal.util.collections.Sets;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class QingYiDaiSpider implements PapaSpider,AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private byte[] gifImage = null;
	private String decodeGif = "";
	private boolean checkTel = false;

	@Override
	public String message() {
		return "轻易贷开元金融旗下品牌_网络借贷信息中介服务平台。2014年上线,实缴注册资金25亿,有银行存管保障用户资金安全,且交易规模已突破900亿。切实帮助小微企业解决融资难、融资贵的问题。";
	}

	@Override
	public String platform() {
		return "qingyidai";
	}

	@Override
	public String home() {
		return "qingyidai.com";
	}

	@Override
	public String platformName() {
		return "轻易贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "小微贷", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("13910252045", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.get("https://www.qingyidai.com/usermanagement/backpassword.shtml");
			Thread.sleep(3000);
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("inputPhone").sendKeys(account);
			chromeDriver.findElementByCssSelector("div[class='control-row cl'] input[type='submit']").click();
			Thread.sleep(5000);
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("imgCodeInput");
				validate.clear();
				validate.sendKeys(decodeGif);
				chromeDriver.findElementByCssSelector(".pop-pay input.button").click();
				Thread.sleep(3000);
				if (chromeDriver.checkElement(".pop-pay")) {
					continue;
				}
				if (chromeDriver.checkElement("#inputPhone-error")) {
					return false;
				}
				if (chromeDriver.checkElement("#msgBtn")) {
					return true;
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
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("nbigfront/entrance/qyduser/getImageCode").isAny().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		gifImage = contents.getBinaryContents();
		System.out.println("捕获到gif");
		new Thread() {
			public void run() {
				GifDecoder gifDecoder = new GifDecoder();
				gifDecoder.read(gifImage);
				int frameCount = gifDecoder.getFrameCount();
				if (frameCount >= 2) {
					byte[] frameBody = gifDecoder.getFrameAsBytes(1);
					decodeGif = OCRDecode.decodeImageCode(frameBody);
				}
			};
		}.start();
	}

}
