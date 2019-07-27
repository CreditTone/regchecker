package com.jisucloud.clawler.regagent.service.impl.social;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class LiaoTianBaoSpider extends PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "聊天宝，原名子弹短信，最大的特色就是可以通过聊天、购物、游戏或完成任务赚取金币，随后可以通过金币按一定比例转换为现金，不过，现金余额超过30元才支持提现。";
	}

	@Override
	public String platform() {
		return "zidanduanxin";
	}

	@Override
	public String home() {
		return "zidanduanxin.com";
	}

	@Override
	public String platformName() {
		return "聊天宝";
	}

	@Override
	public String[] tags() {
		return new String[] { "社交", "资讯" };
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210530000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			String url = "https://im.zidanduanxin.com/login";
			chromeDriver.setAjaxListener(new AjaxListener() {

				@Override
				public String matcherUrl() {
					return "im/tokens";
				}

				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().contains("65");
				}

				@Override
				public String[] blockUrl() {
					return null;
				}

				@Override
				public String fixPostData() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String fixGetData() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			chromeDriver.get(url);smartSleep(2000);
			chromeDriver.findElementByLinkText("使用密码登录").click();smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[placeholder='手机号']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[placeholder='密码']").sendKeys("xoax2mxcndn");
			chromeDriver.findElementByCssSelector("div.btn").click();smartSleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
