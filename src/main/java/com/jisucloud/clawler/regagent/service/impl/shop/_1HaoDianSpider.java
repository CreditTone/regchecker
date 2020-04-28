package com.jisucloud.clawler.regagent.service.impl.shop;

import com.deep007.goniub.selenium.mitm.AjaxHook;
import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;
import com.deep007.goniub.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "yhd.com", 
		message = "1号店(yhd.com)网上超市精选全球好货,提供休闲零食、母婴玩具、进口食品、服饰内衣,1号生鲜、家电家居、手机电脑、宠物用品等各个品类的优质商品。", 
		platform = "yhd", 
		platformName = "1号店", 
		tags = { "购物" }, 
		testTelephones = {"13800000000", "18212345678" })
public class _1HaoDianSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://www.baidu.com/s?tn=monline_3_dg&wd=1%E5%8F%B7%E5%BA%97&rn=50&usm=4&ie=utf-8&rsv_cq=%E6%B7%98%E5%AE%9D&rsv_dl=0_right_recommends_merge_20826&cq=%E6%B7%98%E5%AE%9D&srcid=20910&rt=%E5%89%81%E6%89%8B%E5%85%9A%E4%BA%91%E9%9B%86%E7%9A%84%E7%BD%91%E7%AB%99&recid=20826&euri=3c2f2cd78147404892e933e512c306c2");
			chromeDriver.get("http://www.baidu.com/link?url=cukjY5cX9qj1SRnBF9ETfeM0DVvfC1CUczwvyVc0wBa&wd=&eqid=eaf661e4000e2b5c000000025d21a5fb");
			chromeDriver.get("https://passport.yhd.com/passport/register_input.do");smartSleep(2000);
			chromeDriver.findElementById("phone").sendKeys(account);smartSleep(2000);
			chromeDriver.findElementById("userName").click();smartSleep(3000);
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
		checkTel = contents.getTextContents().contains("****");
	}

}
