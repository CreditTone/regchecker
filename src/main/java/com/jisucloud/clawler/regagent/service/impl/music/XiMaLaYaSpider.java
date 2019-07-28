package com.jisucloud.clawler.regagent.service.impl.music;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider(exclude = false, excludeMsg = "响应太慢")
public class XiMaLaYaSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "喜马拉雅，颠覆了传统电台、个人网络电台单调的在线收听模式，让人们不仅能随时随地，听我想听，更能够轻松创建个人电台，随时分享好声音。在喜马拉雅，你随手就能上传声音作品，创建一个专属于自己的个人电台，持续发展积累粉丝并始终和他们连在一起。";
	}

	@Override
	public String platform() {
		return "ximalaya";
	}

	@Override
	public String home() {
		return "ximalaya.com";
	}

	@Override
	public String platformName() {
		return "喜马拉雅";
	}

	@Override
	public String[] tags() {
		return new String[] {"听书", "生活休闲"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13700982607", "18210538513");
	}
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(XiMaLaYaSpider.class);
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, null);
			chromeDriver.get("https://www.ximalaya.com");
			chromeDriver.get("https://www.ximalaya.com/passport/register");
			smartSleep(2000);
			chromeDriver.findElementById("userAccountPhone").sendKeys(account);
			chromeDriver.findElementById("userPwdPhone").click();
			smartSleep(2000);
			String regErrTex = chromeDriver.findElementByCssSelector("div[class='regIcLt inl-b fr formItem'] p.regErrTex").getText();
			if (regErrTex.contains("已注册")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return false;
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
