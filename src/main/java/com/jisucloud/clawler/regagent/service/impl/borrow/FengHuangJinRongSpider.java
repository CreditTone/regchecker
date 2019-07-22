package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class FengHuangJinRongSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "凤凰金融是凤凰卫视集团为全球华人打造的智能投资理财平台，联合卓越的各类金融机构，深度挖掘优质金融资产，为用户提供优质、多元的投资理财产品和定制化的专业智能服务，打造更契合用户自身需求。";
	}

	@Override
	public String platform() {
		return "fengjr";
	}

	@Override
	public String home() {
		return "fengjr.com";
	}

	@Override
	public String platformName() {
		return "凤凰金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15008276300", "18210538513");
	}
	

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeImg");
				img.click();
				smartSleep(1000);
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
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.fengjr.com/cn/");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementByCssSelector("a[fen='pc_topbar_login_register_link']").click();
			smartSleep(1000);
			chromeDriver.findElementByClassName("password-login").click();
			smartSleep(500);
			chromeDriver.findElementById("loginName").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("lvnqwnk12mcxn");
			chromeDriver.findElementByCssSelector("#userLoginPwd .btn-submit").click();
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
		return HookTracker.builder().addUrl("https://www.fengjr.com/common/node/login/pwd").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		try {
			JSONObject result = JSON.parseObject(contents.getTextContents());
			JSONObject error = result.getJSONObject("error");
			checkTel = error.getString("value").equals("8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
