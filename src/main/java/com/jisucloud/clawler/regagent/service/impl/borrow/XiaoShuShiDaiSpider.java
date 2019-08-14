package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;


import org.openqa.selenium.WebElement;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "xiaoshushidai.com", 
		message = "小树时代竭诚为您提供年轻人贷款、创业贷款 、小额贷款、网上贷款等服务,另外小树时代还提供贷款申请、条件、流程、政策等资讯。", 
		platform = "xiaoshushidai", 
		platformName = "小树时代", 
		tags = { "P2P", "借贷" , "消费贷" , "小微金融" }, 
		testTelephones = { "13910252045", "18210538513" })
public class XiaoShuShiDaiSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, true);
			chromeDriver.get("https://www.xiaoshushidai.cn/user-get_password_invest");
			chromeDriver.addAjaxHook(this);
			chromeDriver.findElementById("password-mobile").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("verify-code");
				validate.clear();
				validate.sendKeys("6315");
				chromeDriver.findElementById("get-captcha-btn").click();
				smartSleep(3000);
				if (vcodeSuc) {
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
		if (messageInfo.getOriginalUrl().contains("act=get_register_verify_code")) {
			return DEFAULT_HTTPRESPONSE;
		}
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (messageInfo.getOriginalUrl().contains("act=check_user")) {
			vcodeSuc = true;
			JSONObject result = JSON.parseObject(contents.getTextContents());
			checkTel = result.getIntValue("status") == 1;
		}
	}

}
