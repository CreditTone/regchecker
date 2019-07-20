package com.jisucloud.clawler.regagent.service.impl.money;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
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

import org.openqa.selenium.WebElement;

@Slf4j
@UsePapaSpider
public class KangTaiRenShouSpider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;

	@Override
	public String message() {
		return "泰康人寿保险股份有限公司是中国大型保险金融服务集团，推荐优秀保险理财顾问，提供医疗保险，养老保险，健康保险，儿童保险，意外保险，教育金保险，大病保险等。";
	}

	@Override
	public String platform() {
		return "taikang";
	}

	@Override
	public String home() {
		return "taikang.com";
	}

	@Override
	public String platformName() {
		return "泰康人寿";
	}

	@Override
	public String[] tags() {
		return new String[] {"理财" , "保险" , "健康保险" , "医疗保险"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268900", "18210538513");
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#yzcode");
				img.click();
				smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "ne5");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("http://ecs.tk.cn/eservice/register/findpwd.jsp");
			smartSleep(2000);
			chromeDriver.findElementByCssSelector("#username").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				String imageCode = getImgCode();
				WebElement mark = chromeDriver.findElementByCssSelector("#mark");
				mark.clear();
				mark.sendKeys(imageCode);
				chromeDriver.findElementByCssSelector("input[value='下一步']").click();
				smartSleep(3000);
				if (vs) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return ct;
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
		return HookTracker.builder().addUrl("http://ecs.tk.cn/eservice/change/service").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}
	
	boolean vs = false;
	boolean ct = false;

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (!contents.getTextContents().contains("验证码")) {
			vs = true;
			ct = contents.getTextContents().contains("member_mobile");
		}
	}

}
