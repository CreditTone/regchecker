package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.clawler.regagent.util.StringUtil;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


import org.openqa.selenium.WebElement;

@Slf4j
@PapaSpiderConfig(
		home = "jyblc.com", 
		message = "金元宝——创立于2013年,稳健运营5年的金融科技企业,获中科招商集团3000万战略投资,是国内知名的社交化金融投资服务平台,致力于产业金融。平台具备国家信息安全等级!", 
		platform = "jyblc", 
		platformName = "金元宝", 
		tags = { "消费分期" , "p2p", "借贷" }, 
		testTelephones = { "18210538513", "15161509916" })
public class JinYuanBaoSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imgCode_login");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "n4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}


	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.get("https://jyblc.cn/pay/login?sess_id=1493349-a63c6cdfa6f7da22df9357b475ce");
			chromeDriver.addAjaxHook(this);smartSleep(2000);
			chromeDriver.findElementByCssSelector("#login_tel").sendKeys(account);
			chromeDriver.findElementByCssSelector("#login_pwd").sendKeys("sadas21p0");
			for (int i = 0; i < 5; i++) {
				WebElement login_imgcode = chromeDriver.findElementById("login_imgcode");
				login_imgcode.clear();
				login_imgcode.sendKeys(getImgCode());
				chromeDriver.findElementByCssSelector("#login_submit").click();smartSleep(3000);
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
		return HookTracker.builder().addUrl("pay/dologin").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	
	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String res = StringUtil.unicodeToString(contents.getTextContents());
		if (!contents.getTextContents().contains("验证码不正确")) {
			vcodeSuc = true;
			checkTel = res.contains("密码错误，");
		}
		
	}

}
