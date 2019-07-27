package com.jisucloud.clawler.regagent.service.impl.social;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class YYSpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "YY直播(yy.com)是中国最大的视频直播网站,yy语音官网,提供大神妹子英雄联盟,cf,dota等热门网络和单机游戏视频直播,美女帅哥视频秀场,同城视频互动交友,股票分析培训.速度流畅不卡,数万美女帅哥主播任你看.";
	}

	@Override
	public String platform() {
		return "yy";
	}

	@Override
	public String home() {
		return "yy.com";
	}

	@Override
	public String platformName() {
		return "YY直播";
	}

	@Override
	public String[] tags() {
		return new String[] {"娱乐" , "交友" , "游戏" , "社区"};
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18810038000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(true, true);
			chromeDriver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
			chromeDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
			chromeDriver.getIgnoreTimeout("http://www.yy.com/h/38.html");
			chromeDriver.getIgnoreTimeout("https://zc.yy.com/reg/udb/reg4udb.do?appid=5719&action=3&type=Mobile&mode=udb&fromadv=yy_0.cpuid_0.channel_0&busiurl=http%3A%2F%2Fwww.yy.com%2F3rdLogin%2Freg-login.html");smartSleep(2000);
			chromeDriver.findElementByCssSelector("input[node-name='inMobile']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[node-name='inPassword']").click();smartSleep(1000);
			return chromeDriver.findElementByCssSelector("div[node-name='mobile'] .form_tip").getText().contains("已注册");
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
