package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class GuangJinJinFuSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "广金金服是广州市国资委辖下广州金融控股集团控股,国资背景的P2P网贷平台,稳健可靠,投资人至今零损失,平台利用先进金融科技,解决中小微企业和个人融资需求,服务社会!";
	}

	@Override
	public String platform() {
		return "gzjkp2p";
	}

	@Override
	public String home() {
		return "gzjkp2p.com";
	}

	@Override
	public String platformName() {
		return "广金金服";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.gzjkp2p.com/querytraninput.do");
			chromeDriver.addAjaxHook(this);smartSleep(2000);
			chromeDriver.findElementByCssSelector("#userName").sendKeys(account);
			chromeDriver.findElementByCssSelector("#userRoleInvestor").click();
			chromeDriver.findElementByLinkText("发送验证码").click();smartSleep(3000);
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
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15161509916");
	}

	@Override
	public HookTracker getHookTracker() {
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("sendForgetPwdSms.do").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	boolean checkTel;
	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = !contents.getTextContents().equals("2");
	}

}
