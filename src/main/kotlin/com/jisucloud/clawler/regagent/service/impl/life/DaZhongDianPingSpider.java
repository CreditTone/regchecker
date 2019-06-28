package com.jisucloud.clawler.regagent.service.impl.life;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;


import java.util.Map;
import java.util.Set;

@Slf4j
//@UsePapaSpider
public class DaZhongDianPingSpider implements PapaSpider,AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean success = false;

	@Override
	public String message() {
		return "大众点评网于2003年4月成立于上海。大众点评是中国领先的本地生活信息及交易平台，也是全球最早建立的独立第三方消费点评网站。大众点评不仅为用户提供商户信息、消费点评及消费优惠等信息服务，同时亦提供团购、餐厅预订、外卖及电子会员卡等O2O（Online To Offline）交易服务。";
	}

	@Override
	public String platform() {
		return "dianping";
	}

	@Override
	public String home() {
		return "dianping.com";
	}

	@Override
	public String platformName() {
		return "大众点评";
	}

	@Override
	public String[] tags() {
		return new String[] {"生活" , "消费点评" , "外卖" , "团购"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910250000", "18210538513");
	}
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(DaZhongDianPingSpider.class);
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, false);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://account.dianping.com/account/iframeLogin?callback=EasyLogin_frame_callback0&wide=false&protocol=https:&redir=https%3A%2F%2Fwww.dianping.com%2F##");
			Thread.sleep(2000);
			chromeDriver.findElementByCssSelector(".bottom-password-login").click();
			Thread.sleep(500);
			chromeDriver.findElementById("tab-account").click();
			Thread.sleep(500);
			chromeDriver.findElementById("account-textbox").sendKeys(account);
			chromeDriver.findElementById("password-textbox").sendKeys("xa2jo29smxz");
			for (int i = 0; i < 5; i++) {
				chromeDriver.findElementByCssSelector("#login-button-account").click();
				Thread.sleep(3000);
				if (success) {
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
		return HookTracker.builder()
				.addUrl("account/ajax/checkRisk")
				.addUrl("account/ajax/passwordLogin")
				.isPOST()
				.build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (messageInfo.getOriginalUrl().contains("account/ajax/checkRisk")) {
			System.out.println(contents.getTextContents());
			JSONObject result = JSON.parseObject(contents.getTextContents());
			String riskLevel = result.getJSONObject("msg").getString("riskLevel");
			if (!riskLevel.equals("0")) {
				System.out.println("fix to 0");
				result.getJSONObject("msg").put("riskLevel", "0");
				System.out.println(result);
				contents.setTextContents(result.toJSONString());
			}
		}else {
			success = true;
			checkTel = contents.getTextContents().contains("(105)");
		}
		
	}

}
