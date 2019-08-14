package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep007.spiderbase.util.GifDecoder;
import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "qingyidai.com", 
		message = "轻易贷开元金融旗下品牌_网络借贷信息中介服务平台。2014年上线,实缴注册资金25亿,有银行存管保障用户资金安全,且交易规模已突破900亿。切实帮助小微企业解决融资难、融资贵的问题。", 
		platform = "qingyidai", 
		platformName = "轻易贷", 
		tags = { "P2P", "小微贷", "借贷" }, 
		testTelephones = { "13910250045", "18210538513" })
public class QingYiDaiSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private byte[] gifImage = null;
	private String decode = "";
	private boolean checkTel = false;
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#image");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.get("https://www.qingyidai.com/usermanagement/backpassword.shtml");smartSleep(1000);
			chromeDriver.findElementById("inputPhone").sendKeys(account);
			chromeDriver.findElementByCssSelector("div[class='control-row cl'] input[type='submit']").click();smartSleep(2000);
			for (int i = 0; i < 5; i++) {
				decode = getImgCode();
				WebElement validate = chromeDriver.findElementById("imgCodeInput");
				validate.clear();
				validate.sendKeys(decode);
				chromeDriver.findElementByCssSelector(".pop-pay input.button").click();smartSleep(3000);
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
		new Thread() {
			public void run() {
				GifDecoder gifDecoder = new GifDecoder();
				gifDecoder.read(gifImage);
				int frameCount = gifDecoder.getFrameCount();
				if (frameCount >= 2) {
					byte[] frameBody = gifDecoder.getFrameAsBytes(1);
					decode = OCRDecode.decodeImageCode(frameBody);
				}
			};
		}.start();
	}

}
