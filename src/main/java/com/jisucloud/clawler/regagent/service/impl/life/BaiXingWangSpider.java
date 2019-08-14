package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@PapaSpiderConfig(
		home = "baixing.com", 
		message = "百姓网，最大的分类信息网。您可以免费查找最新最全的二手物品交易、二手车买卖、房屋租售、宠物、招聘、兼职、求职、交友活动及生活服务等分类信息，还能免费发布这些分类信息。", 
		platform = "baixing", 
		platformName = "百姓网", 
		tags = { "o2o", "生活休闲", "求职" , "招聘" , "房产家居" }, 
		testTelephones = { "15101030000", "18210538513" })
public class BaiXingWangSpider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://www.baixing.com/oz/login###");
			smartSleep(2000);
			chromeDriver.findElementByLinkText("百姓网账号登录").click();
			smartSleep(1000);
			chromeDriver.findElementByCssSelector("input[name='identity']").sendKeys(account);
			chromeDriver.findElementByCssSelector("input[name='password']").sendKeys("324nvcxkwfc");
			chromeDriver.findElementById("id_submit").click();
			smartSleep(2000);
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
		return HookTracker.builder().addUrl("https://www.baixing.com/oz/login").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		try {
			Document doc = Jsoup.parse(contents.getTextContents());
			checkTel = doc.select(".alert-error").text().contains("密码");
		} catch (Exception e) {
		}
	}

}
