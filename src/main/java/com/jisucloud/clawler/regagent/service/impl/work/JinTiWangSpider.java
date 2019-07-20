package com.jisucloud.clawler.regagent.service.impl.work;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
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
@UsePapaSpider
public class JinTiWangSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "今题网为全球华人用户提供及时的社区资讯,分类信息覆盖汽车,招聘,物品交易,教育等生活信息网。今题个人空间,论坛,博客等互动交流空间最有效的结合了今题专业房产网。";
	}

	@Override
	public String platform() {
		return "jinti";
	}

	@Override
	public String home() {
		return "jinti.com";
	}

	@Override
	public String platformName() {
		return "今题网";
	}

	@Override
	public String[] tags() {
		return new String[] {"社区", "招聘", "论坛"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13800000000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://passport.jinti.com/user/getpassword.aspx");
			smartSleep(2000);
			chromeDriver.findElementById("txtEmail").sendKeys(account);
			chromeDriver.findElementById("btnGetPassword").click();
			smartSleep(3000);
			if (chromeDriver.checkElement("#rpData_btnSent")) {
				return true;
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
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("register_check_phone.do").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	boolean checkTel = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		System.out.println(contents.getTextContents());
		checkTel = contents.getTextContents().contains("****");
	}

}
