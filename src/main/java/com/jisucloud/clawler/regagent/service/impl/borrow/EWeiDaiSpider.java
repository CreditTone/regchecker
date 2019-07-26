package com.jisucloud.clawler.regagent.service.impl.borrow;

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

import java.util.Map;
import java.util.Set;

@Slf4j
//@UsePapaSpider
public class EWeiDaiSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "e微贷(www.eweidai.com)专注汽车金融。e微贷是由深圳市前海泰丰融通金融服务有限公司运营的一家创新型的p2p投融资网站,为缺乏合适理财项目的个人用户提供高效、透明、便捷的理财服务,通过利用互联网技术。";
	}

	@Override
	public String platform() {
		return "eweidai";
	}

	@Override
	public String home() {
		return "eweidai.com";
	}

	@Override
	public String platformName() {
		return "e微贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, false, CHROME_USER_AGENT);
			chromeDriver.get("https://www.eweidai.com/member/checkPhonerg2.action?jsoncallback=jsonp"+System.currentTimeMillis()+"&phone="+account+"&Rand=0.4430534748062773");
			smartSleep(2000);
			checkTel = chromeDriver.getPageSource().contains("已注册");
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
		return HookTracker.builder().addUrl("member/checkPhonerg2.action").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		checkTel = contents.getTextContents().contains("已注册");
	}

}
