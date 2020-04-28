package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "sogou.com", 
		message = "搜狗搜索是全球第三代互动式搜索引擎,支持微信公众号和文章搜索、知乎搜索、英文搜索及翻译等,通过自主研发的人工智能算法为用户提供专业、精准、便捷的搜索服务。", 
		platform = "sogou", 
		platformName = "搜狗", 
		tags = { "工具", "搜索引擎" }, 
		testTelephones = { "13910000000", "18212345678" })
public class SouGouSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.sogou.com/qq?ru=https%3A%2F%2Fwww.sogou.com%2Flogin%2Fqq_login_callback_page_new.html%3Fxy%3Dhttps%26from%3Dhttps%253A%252F%252Fwww.sogou.com%252F");smartSleep(3000);
			chromeDriver.mouseClick(chromeDriver.findElementById("sogou_login"));
			chromeDriver.keyboardInput(chromeDriver.findElementByCssSelector("input[class='grey']"), account);smartSleep(1000);
			String han = chromeDriver.getWindowHandle();
			chromeDriver.findElementByCssSelector(".txt-02").click();smartSleep(3000);
			chromeDriver.switchTo().window(han);
			WebElement pwd1 = chromeDriver.findElementByCssSelector(".txt-02 #password_input_fake");
			WebElement pwd2 = chromeDriver.findElementByCssSelector(".txt-02 #password_input");
			chromeDriver.jsInput(pwd1, "cas131231");
			chromeDriver.jsInput(pwd2, "cas131231");
			for (int i = 0; i < 5; i++) {
				chromeDriver.findElementByCssSelector("a.login-btn1").click();smartSleep(2000);
				String error1 = chromeDriver.findElementByCssSelector(".txt-01 p.error-p").getText();
				String error2 = chromeDriver.findElementByCssSelector(".txt-02 p.error-p").getText();
				if (error1.contains("未注册")) {
					return false;
				}
				if (error2.contains("错误")) {
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
		return null;
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		
	}

}
