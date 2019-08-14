package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "ganji.com", 
		message = "赶集网是更专业的分类信息网!提供免费发布信息,查阅信息服务.寻找更新更全的房屋出租、二手房、二手车、二手物品交易、求职招聘等生活信息,请到赶集网ganji.com。", 
		platform = "ganji", 
		platformName = "赶集网", 
		tags = { "o2o", "生活休闲", "求职" , "招聘" , "二手物品" }, 
		testTelephones = { "18210538513", "18210538511" })
public class GanJiWangSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("https://passport.ganji.com/register.php?next=/");
			smartSleep(1000);
			chromeDriver.findElementByCssSelector("input[class='tab-input-text t_reg_phone']").sendKeys(account);
			chromeDriver.findElementByCssSelector("#checkcode_phone_input").click();
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
		return HookTracker.builder().addUrl("https://passport.ganji.com/ajax.php?module=check_phone_by_reg").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String result = StringUtil.unicodeToString(contents.getTextContents());
		checkTel = result.contains("不可作为登录");
	}

}
