package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
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
import org.openqa.selenium.interactions.Actions;

import java.util.Map;
import java.util.Set;

@Slf4j
//@UsePapaSpider  行为分析反扒
public class DouYuSpider implements PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "斗鱼- 每个人的直播平台提供高清、快捷、流畅的视频直播和游戏赛事直播服务,包含英雄联盟lol直播、穿越火线cf直播、dota2直播、美女直播等各类热门游戏赛事直播。";
	}

	@Override
	public String platform() {
		return "douyu";
	}

	@Override
	public String home() {
		return "douyu.com";
	}

	@Override
	public String platformName() {
		return "斗鱼直播";
	}

	@Override
	public String[] tags() {
		return new String[] {"社区", "游戏" , "交友"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13925306966", "18210538513");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imgObj");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	String code;

	@Override
	public boolean checkTelephone(String account) {
		HookTracker hookTracker = HookTracker.builder()
				.addUrl("member/findpassword/findPhoneShow")
				.isPOST().build();
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, false);
			chromeDriver.get("https://www.douyu.com");
			chromeDriver.get("https://www.douyu.com/member/findpassword/findByPhone");
			Thread.sleep(5000);
			chromeDriver.addAjaxHook(new AjaxHook() {
				
				@Override
				public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
					vcodeSuc = true;
					checkTel = true;
				}
				
				@Override
				public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
					return null;
				}

				@Override
				public HookTracker getHookTracker() {
					return hookTracker;
				}
			});
			WebElement tip = chromeDriver.findElementByCssSelector("span[class='geetest_radar_tip_content']");
			Actions actions = new Actions(chromeDriver);
			actions.moveByOffset(tip.getLocation().x+30, tip.getLocation().y+100).perform();
			actions.moveToElement(tip).perform();;
			actions.click().perform();;
			Thread.sleep(3000);
			String vtext = chromeDriver.findElementByClassName("geetest_success_radar_tip_content").getText();
			if (vtext.contains("成功")) {
				chromeDriver.findElementById("reg_userphone").sendKeys(account);
				chromeDriver.findElementById("submit-fp-ph").click();
				Thread.sleep(3000);
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

}
