package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

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
public class MeiTuanSpider extends PapaSpider implements AjaxHook {
	
	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "美团网精选美食餐厅,酒店预订,电影票,旅游景点,外卖订餐,团购信息,您可查询商家评价店铺信息。生活,下载美团官方APP ,吃喝玩乐1折起。";
	}

	@Override
	public String platform() {
		return "meituan";
	}

	@Override
	public String home() {
		return "meituan.com";
	}

	@Override
	public String platformName() {
		return "美团网";
	}

	@Override
	public String[] tags() {
		return new String[] {"o2o", "外卖", "电影票" , "酒店" , "共享单车"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "18210530000");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("span[class='refresh kv-v'] img");
				img.click();smartSleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(false, true, ANDROID_USER_AGENT);
			chromeDriver.get("https://i.meituan.com/risk2/resetreq");
			smartSleep(1000);
			chromeDriver.get("https://i.meituan.com/risk2/resetreq");
			smartSleep(2000);
			WebElement nameInputArea = chromeDriver.findElementByCssSelector("input[name='user']");
			nameInputArea.sendKeys(account);
			for (int i = 0 ; i < 5 ; i ++) {
				WebElement captcha = chromeDriver.findElementByCssSelector("input[name='captcha']");
				captcha.sendKeys(getImgCode());
				chromeDriver.findElementByCssSelector("button[type='submit']").click();
				smartSleep(2000);
				String res = chromeDriver.getPageSource();
				if (res.contains("验证码不正确")) {
					continue;
				}
				String pageSource = chromeDriver.getPageSource();
				checkTel = pageSource.contains("选择验证方式") || pageSource.contains("有风险");
				break;
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
		return null;
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		
	}

}
