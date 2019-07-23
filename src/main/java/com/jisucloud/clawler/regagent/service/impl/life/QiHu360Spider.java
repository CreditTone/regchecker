package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class QiHu360Spider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	

	@Override
	public String message() {
		return "奇虎360是（北京奇虎科技有限公司）的简称，由周鸿祎于2005年9月创立，主营360杀毒为代表的免费网络安全平台和拥有问答等独立业务的公司。";
	}

	@Override
	public String platform() {
		return "360";
	}

	@Override
	public String home() {
		return "360.cn";
	}

	@Override
	public String platformName() {
		return "360";
	}

	@Override
	public String[] tags() {
		return new String[] {"系统工具", "游览器"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13010002005", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("http://i.360.cn/login");
			chromeDriver.findElementByCssSelector("input[name='userName']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[name='password']").sendKeys("msadfhi3jvo");
			chromeDriver.findElementByCssSelector("input[class='quc-button-submit quc-button quc-button-primary']").click();
			smartSleep(3000);
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
		return HookTracker.builder().addUrl("https://login.360.cn/").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String res = URLDecoder.decode(contents.getTextContents());
		checkTel = res.contains("密码");
	}

}
