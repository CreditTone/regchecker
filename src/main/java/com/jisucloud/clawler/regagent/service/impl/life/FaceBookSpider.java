package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "facebook.com", 
		message = "Facebook（中文称脸书，也有译为脸谱网）是美国的一个社交网络服务网站 ，创立于2004年2月4日，总部位于美国加利福尼亚州门洛帕克。2012年3月6日发布Windows版桌面聊天软件Facebook Messenger。主要创始人马克·扎克伯格。", 
		platform = "facebook", 
		platformName = "facebookName", 
		tags = { "社交" , "社区" }, 
		testTelephones = { "13910002005", "18212345678" },
		exclude = true)
public class FaceBookSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithGoogleProxy(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://zh-cn.facebook.com/login/identify/?ctx=recover");
			smartSleep(2000);
			chromeDriver.findElementById("identify_email").sendKeys("+86"+account);
			chromeDriver.findElementById("did_submit").click();
			smartSleep(3000);
			String uiHeaderTitle = chromeDriver.findElementByCssSelector("h2[class='uiHeaderTitle']").getText();
			return uiHeaderTitle.contains("重置密码");
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
