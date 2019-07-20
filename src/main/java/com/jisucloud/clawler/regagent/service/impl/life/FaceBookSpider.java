package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class FaceBookSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "Facebook（中文称脸书，也有译为脸谱网）是美国的一个社交网络服务网站 ，创立于2004年2月4日，总部位于美国加利福尼亚州门洛帕克。2012年3月6日发布Windows版桌面聊天软件Facebook Messenger。主要创始人马克·扎克伯格。";
	}

	@Override
	public String platform() {
		return "facebook";
	}

	@Override
	public String home() {
		return "facebook.com";
	}

	@Override
	public String platformName() {
		return "Facebook";
	}

	@Override
	public String[] tags() {
		return new String[] {"社交" , "社区"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910002005", "18210538513");
	}
	

	@Override
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
